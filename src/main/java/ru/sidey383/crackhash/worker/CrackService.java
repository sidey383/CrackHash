package ru.sidey383.crackhash.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.sidey383.crackhash.internal.dto.ManagerCallbackRequest;
import ru.sidey383.crackhash.worker.permutation.IterableWords;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrackService {

    private final ExecutorService executors = Executors.newCachedThreadPool();

    private final ManagerNodeProvider managerNodeProvider;

    public String startCrack(String hash, Collection<Character> alphabet, int length, long partCount, long partNumber) throws NoSuchAlgorithmException {
        IterableWords words = new IterableWords(length, alphabet.stream().distinct().toList());
        BigInteger totalCount = words.getTotalWordNumber();
        BigInteger start = totalCount.multiply(BigInteger.valueOf(partNumber)).divide(BigInteger.valueOf(partCount));
        BigInteger end = totalCount.multiply(BigInteger.valueOf(partNumber + 1)).divide(BigInteger.valueOf(partCount));
        BigInteger count = end.add(start.negate());
        words.skip(start);
        words.setWordLimit(count);
        String taskId = UUID.randomUUID().toString();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        PartCrackTask task = new PartCrackTask(
                (list) -> completeTask(taskId, list),
                digest,
                HexFormat.of().parseHex(hash),
                words
        );
        log.debug("Start partial crack task. Check {} worlds of {}. From {} to {}", count, totalCount, start, end);
        executors.execute(task);
        return taskId;
    }

    public void completeTask(String taskId, List<String> result) {
        log.debug("Task {} completed with {} results", taskId, result.size());
        ManagerCallbackRequest request = new ManagerCallbackRequest(taskId, result);
        try {
            managerNodeProvider.sendAnswer(request);
        } catch (RestClientException e) {
            log.error("Fail to send result" ,e);
        } catch (URISyntaxException e) {
            log.error("Wrong uri syntax for manager", e);
        }
    }

}
