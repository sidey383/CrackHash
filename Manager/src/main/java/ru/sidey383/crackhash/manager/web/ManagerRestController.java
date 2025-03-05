package ru.sidey383.crackhash.manager.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sidey383.crackhash.manager.ManagerCrackService;
import ru.sidey383.crackhash.manager.dto.CrackStartAnswer;
import ru.sidey383.crackhash.manager.dto.CrackStartRequest;
import ru.sidey383.crackhash.manager.dto.CrackStatusAnswer;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class ManagerRestController {

    private static final Set<Character> alphabet = Stream.concat(
            IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c),
            IntStream.rangeClosed('0', '9').mapToObj(c -> (char) c)
    ).collect(Collectors.toUnmodifiableSet());

    private final ManagerCrackService managerCrackService;

    @PostMapping(APIManagerEndpoint.HASH_CRACK)
    public CrackStartAnswer createCrack(
            @Valid @RequestBody
            CrackStartRequest request
    ) {
        String requestId = managerCrackService.createRequest(request.hash(), request.maxLength(), alphabet);
        return new CrackStartAnswer(requestId);
    }

    @GetMapping(APIManagerEndpoint.HASH_STATUS)
    public CrackStatusAnswer crackStatus(
            @RequestParam("requestId")
            @NotBlank
            String requestId
    ) {
        return managerCrackService.getStatus(requestId);
    }

}
