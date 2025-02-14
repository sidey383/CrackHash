package ru.sidey383.crackhash.manager;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ManagerCrackService {

    private final Map<UUID, Object> requests = new ConcurrentHashMap<>();

    public String createRequest(String hash, long maxLength) {
        UUID uuid = UUID.randomUUID();
        requests.put(uuid, new Object());
        return uuid.toString();
    }

}
