package ru.sidey383.crackhash.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sidey383.crackhash.core.ServiceException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class WorkerNodeProvider {

    private final List<String> servers = List.of("http://worker-1", "http://worker-2", "http://worker-3", "http://worker-4");

    public List<WorkerNodeClient> getActualNodes() {
        List<WorkerNodeClient> nodes = new ArrayList<>(servers.size());
        for (String server : servers) {
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
