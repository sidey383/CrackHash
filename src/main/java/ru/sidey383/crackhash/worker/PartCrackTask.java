package ru.sidey383.crackhash.worker;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PartCrackTask implements Runnable {

    @NotNull
    private final Consumer<List<String>> callback;
    @NotNull
    private final MessageDigest digest;
    private final byte @NotNull [] hash;
    @NotNull
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
