package ru.sidey383.crackhash.worker;

import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PartCrackTask implements Runnable {

    private final Consumer<List<String>> callback;
    private final MessageDigest digest;
    private final byte[] hash;
    private final Iterable<String> words;

    @Override
    public void run() {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            byte[] currentHash = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            if (Arrays.compare(hash, currentHash) == 0) {
                result.add(word);
            }
        }
        callback.accept(result);
    }
}
