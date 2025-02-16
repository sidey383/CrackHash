package ru.sidey383.crackhash.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class CrackTask {

    private final Map<String, WorkerNodeStatus> workerStatuses = new ConcurrentHashMap<>();

    public void addWorker(@NotNull String taskId, long partNumber) {
        this.workerStatuses.put(taskId, new WorkerNodeStatus(taskId, partNumber, Optional.empty()));
    }

    record WorkerNodeStatus(@NotNull String taskId, long partNumber, @NotNull Optional<List<String>> result) {
    }

    public boolean isComplete() {
        return workerStatuses.values().stream().map(WorkerNodeStatus::result).allMatch(Optional::isPresent);
    }

    public void setResult(@NotNull String taskId, @NotNull List<String> result) {
        workerStatuses.computeIfPresent(taskId, (_, status) -> new WorkerNodeStatus(status.taskId(), status.partNumber(), Optional.of(result)));
    }

    @NotNull
    public List<String> getResult() {
        return workerStatuses.values().stream()
                .map(WorkerNodeStatus::result)
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    @NotNull
    public Set<String> getWorkerTaskIds() {
        return Collections.unmodifiableSet(workerStatuses.keySet());
    }

}
