package ru.sidey383.crackhash.manager;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sidey383.crackhash.core.ServiceException;
import ru.sidey383.crackhash.core.dto.WorkerPartialCrackRequest;

import java.net.URI;


@RequiredArgsConstructor
public class WorkerNodeClient {

    @Getter
    private final URI uri;
    private final RestTemplate restTemplate = new RestTemplate();

    @NotNull
    public void sendRequest(@NotNull WorkerPartialCrackRequest request) throws RestClientException, ServiceException {
        URI target = uri.resolve("/internal/api/worker/hash/crack/task");
        restTemplate.postForEntity(target, request, Void.class);
    }

}
