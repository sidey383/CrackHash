package ru.sidey383.crackhash.worker.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatProducer {

    private final Exchange heartbeatExchange;
    private final RabbitTemplate rabbitTemplate;
    private final UUID workerId;

    @Scheduled(fixedRateString = "${heartbeat.sendRate}")
    public void sendHeartbeat() {
        rabbitTemplate.convertAndSend(heartbeatExchange.getName(), "", workerId.toString());
        log.debug("Send heartbeat as worker {}", workerId);
    }

}
