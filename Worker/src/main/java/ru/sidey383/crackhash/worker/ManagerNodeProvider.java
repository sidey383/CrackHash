package ru.sidey383.crackhash.worker;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sidey383.crackhash.core.dto.ManagerCallbackRequest;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ManagerNodeProvider {

    @NotNull
    @Value("${manager.address}")
    private final String managerAddress;

    private final RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());

    public void sendAnswer(@NotNull ManagerCallbackRequest request) throws RestClientException, URISyntaxException {
        URI uri = new URI(managerAddress);
        URI target = uri.resolve("/internal/api/manager/hash/crack/request");
        restTemplate.patchForObject(target, request, Void.class);
    }

}
