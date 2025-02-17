package ru.sidey383.crackhash.worker;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.sidey383.crackhash.core.APIWorkerEndpoint;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@AllArgsConstructor
public class WorkerRestController {

    private final CrackService crackService;

    @PostMapping(APIWorkerEndpoint.INTERNAL_WORKER_HASH_CRACK_TASK)
    public void createRequest(
            @Valid @RequestBody
            CrackHashManagerRequest request
    ) throws NoSuchAlgorithmException {
        List<Character> alphabet = request.getAlphabet().getSymbols().stream()
                .filter(Strings::isNotBlank)
                .map(c -> c.charAt(0))
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
