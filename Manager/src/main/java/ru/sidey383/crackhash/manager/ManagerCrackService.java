package ru.sidey383.crackhash.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.sidey383.crackhash.core.ErrorStatus;
import ru.sidey383.crackhash.core.ServiceException;
import ru.sidey383.crackhash.manager.data.CrackTask;
import ru.sidey383.crackhash.manager.data.CrackTaskRepository;
import ru.sidey383.crackhash.manager.dto.CrackStatus;
import ru.sidey383.crackhash.manager.dto.CrackStatusAnswer;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerCrackService {

    private final CrackTaskRepository crackTaskRepository;
    private final WorkerNodeProvider workerNodeProvider;

    @NotNull
    public String createRequest(@NotNull String hash, int maxLength, @NotNull Set<Character> alphabet) throws ServiceException {
        List<WorkerNodeClient> clients = workerNodeProvider.getActualNodes();
        CrackTask task = crackTaskRepository.save(new CrackTask(hash, maxLength, alphabet, clients.size()));
        for (int i = 0; i < clients.size(); i++) {
            WorkerNodeClient client = clients.get(i);
            try {
                log.debug("Try to send task for worker {}, partialCount={}, partialNumber={}", client.getUri(), clients.size(), i);
                CrackHashManagerRequest request = new CrackHashManagerRequest();
                request.setHash(hash);
                request.setRequestId(task.getUuid().toString());
                request.setMaxLength(maxLength);
                request.setPartNumber(i);
                request.setPartCount(clients.size());
                var alp = new CrackHashManagerRequest.Alphabet();
                alp.getSymbols().addAll(alphabet.stream().map(String::valueOf).toList());
                request.setAlphabet(alp);
                client.sendRequest(request);
            } catch (RestClientException e) {
                log.error("Worker {} request error", client.getUri() , e);
                throw new ServiceException("Worker request failed", e);
            }
        }
        log.info("Start task with id {}", task.getUuid().toString());
        return task.getUuid().toString();
    }

    @Transactional
    public void applyWorkerResult(@NotNull String requestId, int partNumber, @NotNull List<String> results) {
        log.info("Apply worker results for task {} part {} count of result {}", requestId, partNumber, results.size());
        CrackTask task = foundTaskOrThrow(requestId);
        if (partNumber < 0 || partNumber >= task.getPartCount()) throw new ServiceException(ErrorStatus.INTERNAL_ERROR, "That part doesn't exist");
        task.setResult(partNumber, results);
        crackTaskRepository.save(task);
    }

    @NotNull
    public CrackStatusAnswer getStatus(@NotNull String taskId) throws ServiceException {
        log.debug("Status request by taskId {}", taskId);
        CrackTask task = foundTaskOrThrow(taskId);
        if (task.isComplete()) {
            return new CrackStatusAnswer(CrackStatus.READY, task.getResult());
        } else {
            return new CrackStatusAnswer(CrackStatus.IN_PROGRESS, null);
        }
    }

    private CrackTask foundTaskOrThrow(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(ErrorStatus.WRONG_ARGS, "Wrong task id");
        }
        return crackTaskRepository.findById(uuid).orElseThrow(() -> new ServiceException(ErrorStatus.WRONG_ARGS, "Wrong task id"));
    }

}
