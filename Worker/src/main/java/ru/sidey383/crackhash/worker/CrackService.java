package ru.sidey383.crackhash.worker;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;
import ru.sidey383.crackhash.worker.permutation.IterableWords;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrackService {

    private final ExecutorService executors = Executors.newCachedThreadPool();

    private final ManagerNodeProvider managerNodeProvider;

    public void startCrack(
            @NotNull String requestId,
            @NotNull String hash,
            @NotNull Collection<Character> alphabet,
            int length, int partCount, int partNumber
    ) throws NoSuchAlgorithmException {
        IterableWords words = new IterableWords(length, alphabet.stream().distinct().toList());
        BigInteger totalCount = words.getTotalWordNumber();
        BigInteger start = totalCount.multiply(BigInteger.valueOf(partNumber)).divide(BigInteger.valueOf(partCount));
        BigInteger end = totalCount.multiply(BigInteger.valueOf(partNumber + 1)).divide(BigInteger.valueOf(partCount));
        BigInteger count = end.add(start.negate());
        words.skip(start);
        words.setWordLimit(count);
        MessageDigest digest = MessageDigest.getInstance("MD5");
        PartCrackTask task = new PartCrackTask(
                (list) -> completeTask(requestId, partNumber, list),
                digest,
                HexFormat.of().parseHex(hash),
                words
        );
        log.info("Start partial crack task {} with part number {}. Check {} worlds of {}. From {} to {}", requestId, partNumber, count, totalCount, start, end);
        executors.execute(task);
    }

    public void completeTask(@NotNull String requestId, int partNumber, @NotNull List<String> result) {
        log.info("Complete partial crack task Task {} part {} completed with {} results", requestId, partNumber, result.size());
        CrackHashWorkerResponse request = new CrackHashWorkerResponse();
        request.setRequestId(requestId);
        request.setPartNumber(partNumber);
        var answer = new CrackHashWorkerResponse.Answers();
        answer.getWords().addAll(result);
        request.setAnswers(answer);
        try {
            managerNodeProvider.sendAnswer(request);
        } catch (RestClientException e) {
            log.error("Fail to send result" ,e);
        } catch (URISyntaxException e) {
            log.error("Wrong uri syntax for manager", e);
        }
    }

}
