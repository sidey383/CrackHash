package ru.sidey383.crackhash.worker.permutation;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class IterableWords implements Iterable<String> {

    @NotNull
    private BigInteger startPose = BigInteger.ZERO;
    @Nullable
    private BigInteger limit = null;
    private final int maxLength;
    @NotNull
    private final List<Character> alphabet;

    @NotNull
    @Override
    public Iterator<String> iterator() {
        Iterator<String> iterator = new StringWordIterator(startPose, maxLength, alphabet);
        if (limit != null) {
            iterator = new LimitedIterator<>(iterator, limit);
        }
        return iterator;
    }

    public void skip(@NotNull BigInteger count) {
        this.startPose = startPose.add(count);
    }

    public void setWordLimit(@NotNull BigInteger limit) {
        this.limit = limit;
    }

    @NotNull
    public BigInteger getTotalWordNumber() {
        return PermutationUtils.getCountOfTotalPermutation(maxLength, alphabet.size());
    }

    @NotNull
    public BigInteger getAvailableWordNumber() {
        return getTotalWordNumber().add(startPose.negate());
    }
}
