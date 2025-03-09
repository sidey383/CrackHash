package ru.sidey383.crackhash.worker.messaging;

import com.rabbitmq.client.Channel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import ru.sidey383.crackhash.core.dto.CrackHashManagerRequest;
import ru.sidey383.crackhash.worker.CrackService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WorkListener {

    private final CrackService crackService;
    @Qualifier("crackWorkerExecutor")
    private final TaskExecutor executor;

    @RabbitListener(queues = "#{crackRequestBinding.destination}", containerFactory = "crackWorkerListenerContainer")
    public void onCrackHashWorkerResponse(@Valid @Payload CrackHashManagerRequest request, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        try {
            executor.execute(() -> {
                try {
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
                    channel.basicAck(tag, false);
                } catch (Exception ce) {
                    log.error("Fail to consume request {} part {}", request.getRequestId(), request.getPartCount(), ce);
                    try {
                        channel.basicReject(tag, true);
                    } catch (IOException cre) {
                        log.error("Fail to reject request {} part {}", request.getRequestId(), request.getPartCount(), cre);
                    }
                }
            });
        } catch (TaskRejectedException tre) {
            log.error("Task for {} part {} was rejected", request.getRequestId(), request.getPartCount(), tre);
            try {
                channel.basicReject(tag, true);
            } catch (IOException cre) {
                log.error("Fail to reject request {} part {}", request.getRequestId(), request.getPartCount(), cre);
            }
        }
    }

}
