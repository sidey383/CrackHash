package ru.sidey383.crackhash.manager.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.sidey383.crackhash.core.dto.CrackHashManagerRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WorkPublisher {

    @Qualifier("crackRequestBinding")
    private final Binding binding;
    private final RabbitTemplate rabbitTemplate;

    public void sendWork(CrackHashManagerRequest request) {
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), request);
    }

}
