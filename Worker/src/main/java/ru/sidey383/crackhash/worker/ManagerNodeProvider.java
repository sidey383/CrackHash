package ru.sidey383.crackhash.worker;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;
import ru.sidey383.crackhash.core.APIManagerEndpoint;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ManagerNodeProvider {

    @NotNull
    @Value("${manager.address}")
    private final String managerAddress;
    private final RestTemplate restTemplate;

    public void sendAnswer(@NotNull CrackHashWorkerResponse request) throws RestClientException, URISyntaxException {
        URI uri = new URI(managerAddress);
        URI target = uri.resolve(APIManagerEndpoint.INTERNAL_MANAGER_HASH_CRACK_REQUEST);
        restTemplate.patchForObject(target, request, Void.class);
    }

}
