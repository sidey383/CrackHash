package ru.sidey383.crackhash.manager;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sidey383.crackhash.core.ErrorStatus;
import ru.sidey383.crackhash.core.ServiceException;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackAnswer;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackRequest;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public class WorkerNodeClient {

    private final URI uri;
    private final RestTemplate restTemplate = new RestTemplate();

    @NotNull
    public WorkerPartialCrackAnswer sendRequest(@NotNull WorkerPartialCrackRequest request) throws RestClientException, ServiceException {
        URI target = uri.resolve("/internal/api/worker/hash/crack/task");
        WorkerPartialCrackAnswer answer = restTemplate.postForObject(target, request, WorkerPartialCrackAnswer.class);
        if (answer == null)
            throw new ServiceException(ErrorStatus.INTERNAL_ERROR ,"No response received from worker");
        return answer;
    }

}
