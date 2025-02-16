package ru.sidey383.crackhash.worker.permutation;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.Iterator;

@RequiredArgsConstructor
public class LimitedIterator<T> implements Iterator<T> {

    private BigInteger consumed = BigInteger.ZERO;
    private final Iterator<T> original;
    private final BigInteger limit;

    @Override
    public boolean hasNext() {
        return original.hasNext() && consumed.compareTo(limit) < 0;
    }

    @Override
    public T next() {
        T result = original.next();
        consumed = consumed.add(BigInteger.ONE);
        return result;
    }
}
