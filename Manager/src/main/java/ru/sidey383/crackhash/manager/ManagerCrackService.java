package ru.sidey383.crackhash.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.AmqpException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sidey383.crackhash.manager.exception.ErrorStatus;
import ru.sidey383.crackhash.manager.exception.ServiceException;
import ru.sidey383.crackhash.core.dto.CrackHashManagerRequest;
import ru.sidey383.crackhash.manager.data.CrackTask;
import ru.sidey383.crackhash.manager.data.CrackTaskRepository;
import ru.sidey383.crackhash.manager.dto.CrackStatus;
import ru.sidey383.crackhash.manager.dto.CrackStatusAnswer;
import ru.sidey383.crackhash.manager.messaging.HeartbeatListener;
import ru.sidey383.crackhash.manager.messaging.WorkPublisher;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerCrackService {

    private final CrackTaskRepository crackTaskRepository;
    private final HeartbeatListener heartbeatListener;
    private final WorkPublisher workPublisher;

    @NotNull
    @Transactional
    public String createRequest(@NotNull String hash, int maxLength, @NotNull Set<Character> alphabet) {
        final int partCount = Math.max(3, heartbeatListener.getActiveWorkers().size());
        CrackTask task = crackTaskRepository.save(new CrackTask(hash, maxLength, alphabet, partCount));
        log.info("Create task with id {}", task.getUuid().toString());
        try {
            startTask(task);
        } catch (AmqpException e) {
            log.error("Fail to start task {}", task.getUuid(), e);
        }
        return task.getUuid().toString();
    }

    @Scheduled(fixedRateString = "${manager.sendRetry}")
    public void retryScheduler() {
        log.debug("Start retry scheduler");
        for (CrackTask task : crackTaskRepository.getNotSentTasks()) {
            log.debug("Found task {} for retry", task.getUuid());
            try {
                startTask(task);
            } catch (AmqpException e) {
                log.error("Fail to retry start task {}", task.getUuid(), e);
            }
        }
    }

    private void startTask(CrackTask task) {
        for (int i = 0; i < task.getPartCount(); i++) {
            log.debug("Try to send task for partialCount={}, partialNumber={}", task.getPartCount(), i);
            workPublisher.sendWork(CrackHashManagerRequest.builder()
                    .hash(task.getHash())
                    .requestId(task.getUuid().toString())
                    .maxLength(task.getMaxLength())
                    .partNumber(i)
                    .partCount(task.getPartCount())
                    .alphabet(task.getAlphabet().stream().toList())
                    .build()
            );
        }
        crackTaskRepository.markRequestAsSent(task.getUuid());
        log.info("Start task with id {}", task.getUuid());
    }

    @Transactional
    public void applyWorkerResult(@NotNull String requestId, int partNumber, @NotNull List<String> results) {
        log.info("Apply worker results for task {} part {} count of result {}", requestId, partNumber, results.size());
        CrackTask task = foundTaskOrThrow(requestId);
        if (partNumber < 0 || partNumber >= task.getPartCount())
            throw new ServiceException(ErrorStatus.INTERNAL_ERROR, "That part doesn't exist");
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
        log.debug("Try to found task by id \"{}\"", id);
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(ErrorStatus.WRONG_ARGS, "Wrong task id");
        }
        return crackTaskRepository.findById(uuid).orElseThrow(() -> new ServiceException(ErrorStatus.WRONG_ARGS, "Wrong task id"));
    }

}
