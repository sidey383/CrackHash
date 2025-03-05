package ru.sidey383.crackhash.manager.messaging;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.sidey383.crackhash.core.dto.CrackHashWorkerResponse;
import ru.sidey383.crackhash.manager.ManagerCrackService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ManagerQueueListener {

    private final ManagerCrackService managerCrackService;

    @RabbitListener(queues = "#{crackResultBinding.destination}")
    public void onCrackHashWorkerResponse(@Valid @Payload CrackHashWorkerResponse callbackRequest) {
        managerCrackService.applyWorkerResult(
                callbackRequest.getRequestId(),
                callbackRequest.getPartNumber(),
                callbackRequest.getResult()
        );
        log.debug("Receive crack hash worker response {}", callbackRequest);
    }

}
