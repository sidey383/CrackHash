package ru.sidey383.crackhash.manager.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@DependsOn("heartbeatBinding")
@RequiredArgsConstructor
public class HeartbeatListener {

    private final Map<String, Long> activeWorkers = new ConcurrentHashMap<>();
    @Value("${heartbeat.timeout}")
    private final Long WORKER_TIMEOUT;

    @RabbitListener(queues = "#{heartbeatBinding.destination}")
    public void onHeartbeat(String workerId) {
        var old = activeWorkers.put(workerId, System.currentTimeMillis());
        log.debug("Receive heartbeat from worker {}", workerId);
        if (old == null) log.info("Found new worker {}", workerId);
    }

    @Scheduled(fixedRateString = "${heartbeat.clearRate}")
    public void clearHeartbeat() {
        long now = System.currentTimeMillis();
        activeWorkers.entrySet().removeIf(entry -> {
            boolean toRemove = now - entry.getValue() > WORKER_TIMEOUT;
            if (toRemove) log.debug("Remove worker {} from active nodes", entry.getKey());
            return toRemove;
        });
    }

    public Set<String> getActiveWorkers() {
        return Set.copyOf(activeWorkers.keySet());
    }

}
