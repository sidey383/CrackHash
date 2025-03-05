package ru.sidey383.crackhash.worker.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.sidey383.crackhash.core.dto.CrackHashWorkerResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WorkerResultPublisher {

    @Qualifier("crackResultBinding")
    private final Binding binding;
    private final RabbitTemplate rabbitTemplate;

    public void sendWorkResult(CrackHashWorkerResponse request) {
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), request);
    }

}
