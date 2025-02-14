package ru.sidey383.crackhash.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackAnswer;
import ru.sidey383.crackhash.internal.dto.WorkerPartialCrackRequest;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public class WorkerNodeClient {

    private final URI uri;
    private final RestTemplate restTemplate = new RestTemplate();

    public WorkerPartialCrackAnswer sendRequest(WorkerPartialCrackRequest request) throws RestClientException {
        URI target = uri.resolve("/internal/api/worker/hash/crack/task");
        return restTemplate.postForObject(target, request, WorkerPartialCrackAnswer.class);
    }

}
