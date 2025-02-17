package ru.sidey383.crackhash.manager;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.sidey383.crackhash.core.APIWorkerEndpoint;
import ru.sidey383.crackhash.core.ServiceException;

import java.net.URI;


@RequiredArgsConstructor
public class WorkerNodeClient {

    @Getter
    private final URI uri;
    private final RestTemplate restTemplate;

    @NotNull
    public void sendRequest(@NotNull CrackHashManagerRequest request) throws RestClientException, ServiceException {
        URI target = uri.resolve(APIWorkerEndpoint.INTERNAL_WORKER_HASH_CRACK_TASK);
        restTemplate.postForEntity(target, request, Void.class);
    }

}
