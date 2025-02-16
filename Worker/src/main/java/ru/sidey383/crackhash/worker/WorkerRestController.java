package ru.sidey383.crackhash.worker;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sidey383.crackhash.core.dto.WorkerPartialCrackRequest;

import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
public class WorkerRestController {

    private final CrackService crackService;

    @PostMapping("/internal/api/worker/hash/crack/task")
    public void createRequest(
            @Valid @RequestBody
            WorkerPartialCrackRequest request
    ) throws NoSuchAlgorithmException {
        crackService.startCrack(
                request.requestId(),
                request.hash(),
                request.alphabet(),
                request.maxLength(),
                request.partCount(),
                request.partNumber()
        );
    }

}
