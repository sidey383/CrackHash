package ru.sidey383.crackhash.manager;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sidey383.crackhash.core.ServiceException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerNodeProvider {

    private final WorkerConfig workerConfig;

    @NotNull
    public List<WorkerNodeClient> getActualNodes() throws ServiceException {
        List<WorkerNodeClient> nodes = new ArrayList<>(workerConfig.getAddress().size());
        for (String server : workerConfig.getAddress()) {
            URI uri;
            try {
                uri = new URI(server);
            } catch (URISyntaxException uriSyntaxException) {
                log.error("Can't parser server URI", uriSyntaxException);
                throw new ServiceException("Wrong configuration", uriSyntaxException);
            }
            nodes.add(new WorkerNodeClient(uri));
        }
        return Collections.unmodifiableList(nodes);
    }

}
