package ru.sidey383.crackhash.worker.permutation;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class IterableWords implements Iterable<String> {

    private BigInteger startPose = BigInteger.ZERO;
    private BigInteger limit = null;
    private final int maxLength;
    private final List<Character> alphabet;

    @Override
    public Iterator<String> iterator() {
        Iterator<String> iterator = new StringWordIterator(startPose, maxLength, alphabet);
        if (limit != null) {
            iterator = new LimitedIterator<>(iterator, limit);
        }
        return iterator;
    }

    public void skip(BigInteger count) {
        this.startPose = startPose.add(count);
    }

    public void setWordLimit(BigInteger limit) {
        this.limit = limit;
    }

    public BigInteger getTotalWordNumber() {
        return PermutationUtils.getCountOfTotalPermutation(maxLength, alphabet.size());
    }

    public BigInteger getAvailableWordNumber() {
        return getTotalWordNumber().add(startPose.negate());
    }
}
