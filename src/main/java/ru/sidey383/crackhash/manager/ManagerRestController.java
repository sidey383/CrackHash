package ru.sidey383.crackhash.manager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sidey383.crackhash.internal.dto.ManagerCallbackRequest;
import ru.sidey383.crackhash.manager.dto.CrackStartAnswer;
import ru.sidey383.crackhash.manager.dto.CrackStartRequest;
import ru.sidey383.crackhash.manager.dto.CrackStatus;
import ru.sidey383.crackhash.manager.dto.CrackStatusAnswer;

@RestController
@RequiredArgsConstructor
public class ManagerRestController {

    private final ManagerCrackService managerCrackService;

    @GetMapping("/api/hash/crack")
    public CrackStartAnswer createCrack(
            @Valid @RequestBody
            CrackStartRequest request
    ) {
        String requestId = managerCrackService.createRequest(request.hash(), request.maxLength());
        return new CrackStartAnswer(requestId);
    }

    @GetMapping("/api/hash/status")
    public CrackStatusAnswer crackStatus(
            @RequestParam("requestId")
            String requestId
    ) {
        return new CrackStatusAnswer(CrackStatus.ERROR, null);
    }

    @PatchMapping("/internal/api/manager/hash/crack/request")
    public void crackRequestAnswer(
            @Valid @RequestBody
            ManagerCallbackRequest callbackRequest
    ) {
    }

}
