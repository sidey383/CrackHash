package ru.sidey383.crackhash.worker.messaging;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.sidey383.crackhash.core.dto.CrackHashManagerRequest;
import ru.sidey383.crackhash.worker.CrackService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WorkListener {

    private final CrackService crackService;

    @RabbitListener(queues = "#{crackRequestBinding.destination}", concurrency = "${worker.concurrency:5}")
    public void onCrackHashWorkerResponse(@Valid @Payload CrackHashManagerRequest request) throws NoSuchAlgorithmException {
        List<Character> alphabet = request.getAlphabet().stream()
                .filter(Objects::nonNull)
                .toList();
        crackService.startCrack(
                request.getRequestId(),
                request.getHash(),
                alphabet,
                request.getMaxLength(),
                request.getPartCount(),
                request.getPartNumber()
        );
    }

}
