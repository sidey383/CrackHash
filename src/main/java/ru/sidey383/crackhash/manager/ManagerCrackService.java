package ru.sidey383.crackhash.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.sidey383.crackhash.core.ErrorStatus;
import ru.sidey383.crackhash.core.ServiceException;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackAnswer;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackRequest;
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
    private final Map<String, CrackTask> workerCrackTasks = new ConcurrentHashMap<>();
    private final WorkerNodeProvider workerNodeProvider;

    public String createRequest(String hash, long maxLength, Set<Character> alphabet) {
        String uuid = UUID.randomUUID().toString();
        List<WorkerNodeClient> clients = workerNodeProvider.getActualNodes();
        var requestBuilder = WorkerPartialCrackRequest.builder()
                .hash(hash)
                .alphabet(alphabet)
                .length(maxLength)
                .partCount(clients.size());
        CrackTask task = new CrackTask();
        for (int i = 0; i < clients.size(); i++) {
            WorkerNodeClient client = clients.get(i);
            try {
                WorkerPartialCrackAnswer answer = client.sendRequest(requestBuilder.partNumber(i).build());
                task.addWorker(answer.taskId(), i);
            } catch (RestClientException e) {
                log.error("Worker {} request error", client.getUri() , e);
                throw new ServiceException("Fail", e);
            }
        }
        task.getWorkerTaskIds().forEach(t -> workerCrackTasks.put(t, task));
        requests.put(uuid, task);
        return uuid;
    }

    public void applyWorkerResult(String taskId, List<String> results) {
        workerCrackTasks.get(taskId).setResult(taskId, results);
    }

    public CrackStatusAnswer getStatus(String taskId) {
        CrackTask task = requests.get(taskId);
        if (task == null) throw new ServiceException(ErrorStatus.WRONG_ARGS, "Can't found that task");
        if (task.isComplete()) {
            return new CrackStatusAnswer(CrackStatus.READY, task.getResult());
        } else {
            return new CrackStatusAnswer(CrackStatus.IN_PROGRESS, null);
        }
    }

}
