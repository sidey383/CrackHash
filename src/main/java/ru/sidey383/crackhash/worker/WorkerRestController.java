package ru.sidey383.crackhash.worker;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackAnswer;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackRequest;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class WorkerRestController {

    private final CrackService crackService;

    @PostMapping("/internal/api/worker/hash/crack/task")
    public WorkerPartialCrackAnswer createRequest(WorkerPartialCrackRequest request) {
        return new WorkerPartialCrackAnswer(UUID.randomUUID().toString());
    }

}
