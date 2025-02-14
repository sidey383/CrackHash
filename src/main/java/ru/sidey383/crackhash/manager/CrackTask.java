package ru.sidey383.crackhash.manager;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class CrackTask {

    private final Map<String, WorkerNodeStatus> workerStatuses = new ConcurrentHashMap<>();

    public void addWorker(String taskId, long partNumber) {
        this.workerStatuses.put(taskId, new WorkerNodeStatus(taskId, partNumber, Optional.empty()));
    }

    public record WorkerNodeStatus(String taskId, long partNumber, Optional<List<String>> result) {
    }

    public boolean isComplete() {
        return workerStatuses.values().stream().map(WorkerNodeStatus::result).allMatch(Optional::isPresent);
    }

    public void setResult(@NotNull String taskId, @NotNull List<String> result) {
        workerStatuses.computeIfPresent(taskId, (_, status) -> new WorkerNodeStatus(status.taskId(), status.partNumber(), Optional.of(result)));
    }

    public List<String> getResult() {
        return workerStatuses.values().stream()
                .map(WorkerNodeStatus::result)
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    public Set<String> getWorkerTaskIds() {
        return Collections.unmodifiableSet(workerStatuses.keySet());
    }

}
