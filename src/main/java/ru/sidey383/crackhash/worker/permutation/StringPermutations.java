package ru.sidey383.crackhash.worker.permutation;

import lombok.AllArgsConstructor;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public class StringPermutations implements Iterable<String> {

    private final BigInteger startPose;
    private final int maxLength;
    private final List<Character> alphabet;

    @Override
    public Iterator<String> iterator() {
        return new StringPermutationIterator(startPose, maxLength, alphabet);
    }
}
