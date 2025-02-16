package ru.sidey383.crackhash.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.sidey383.crackhash.core.ErrorStatus;
import ru.sidey383.crackhash.core.ServiceException;
import ru.sidey383.crackhash.core.dto.WorkerPartialCrackRequest;
import ru.sidey383.crackhash.manager.dto.CrackStatus;
import ru.sidey383.crackhash.manager.dto.CrackStatusAnswer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerCrackService {

    private final Map<String, CrackTask> requests = new ConcurrentHashMap<>();
    private final WorkerNodeProvider workerNodeProvider;

    @NotNull
    public String createRequest(@NotNull String hash, int maxLength, @NotNull Set<Character> alphabet) throws ServiceException {
        String uuid = UUID.randomUUID().toString();
        List<WorkerNodeClient> clients = workerNodeProvider.getActualNodes();
        var requestBuilder = WorkerPartialCrackRequest.builder()
                .hash(hash)
                .requestId(uuid)
                .alphabet(alphabet)
                .maxLength(maxLength)
                .partCount(clients.size());
        CrackTask task = new CrackTask(clients.size());
        for (int i = 0; i < clients.size(); i++) {
            WorkerNodeClient client = clients.get(i);
            try {
                log.debug("Try to send task for worker {}, partialCount={}, partialNumber={}", client.getUri(), clients.size(), i);
                client.sendRequest(requestBuilder.partNumber(i).build());
            } catch (RestClientException e) {
                log.error("Worker {} request error", client.getUri() , e);
                throw new ServiceException("Fail", e);
            }
        }
        requests.put(uuid, task);
        log.debug("Create task with id {}", uuid);
        return uuid;
    }

    public void applyWorkerResult(@NotNull String requestId, int partNumber, @NotNull List<String> results) {
        log.info("Apply worker results for task={} part={}, count of result={}", requestId, partNumber, results.size());
        CrackTask task = requests.get(requestId);
        if (task == null) throw new ServiceException(ErrorStatus.WRONG_ARGS, "Can't found that task");
        task.setResult(partNumber, results);
    }

    @NotNull
    public CrackStatusAnswer getStatus(@NotNull String taskId) throws ServiceException {
        log.debug("Status request by taskId {}", taskId);
        CrackTask task = requests.get(taskId);
        if (task == null) throw new ServiceException(ErrorStatus.WRONG_ARGS, "Can't found that task");
        if (task.isComplete()) {
            return new CrackStatusAnswer(CrackStatus.READY, task.getResult());
        } else {
            return new CrackStatusAnswer(CrackStatus.IN_PROGRESS, null);
        }
    }

}
