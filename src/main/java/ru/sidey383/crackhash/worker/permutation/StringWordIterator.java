package ru.sidey383.crackhash.worker.permutation;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class StringWordIterator implements Iterator<String> {

    private BigInteger nextNumber;
    private BigInteger totalLengthNumber;
    private int currentLength;
    private final int maxLength;
    private final List<Character> alphabet;
    private final BigInteger alphabetLength;

    StringWordIterator(BigInteger number, int maxLength, List<Character> alphabet) {
        this.alphabet = Collections.unmodifiableList(alphabet);
        this.maxLength = maxLength;
        final int alphabetLength = alphabet.size();
        BigInteger currentNumber = number;
        BigInteger currentMaxNumber = BigInteger.ZERO;
        int currentLength = 0;
        while (currentLength <= maxLength) {
            currentMaxNumber = PermutationUtils.getCountOfFixLengthPermutation(currentLength, alphabetLength);
            if (currentNumber.compareTo(currentMaxNumber) >= 0) {
                currentNumber = currentNumber.add(currentMaxNumber.negate());
            } else {
                break;
            }
            currentLength++;
        }
        this.nextNumber = currentNumber;
        this.currentLength = currentLength;
        this.totalLengthNumber = currentMaxNumber;
        this.alphabetLength = BigInteger.valueOf(alphabet.size());

    }

    @Override
    public boolean hasNext() {
        return currentLength < maxLength || nextNumber.compareTo(totalLengthNumber) < 0;
    }

    @Override
    public String next() {
        if (nextNumber.compareTo(totalLengthNumber) >= 0) {
            if (currentLength >= maxLength)
                throw new NoSuchElementException("No more permutation");
            nextNumber = BigInteger.ZERO;
            ++currentLength;
            totalLengthNumber = PermutationUtils.getCountOfFixLengthPermutation(currentLength, alphabet.size());
        }
        char[] combination = new char[currentLength];
        BigInteger part = nextNumber;
        for (int i = 0; i < combination.length; i++) {
            BigInteger[] result = part.divideAndRemainder(alphabetLength);
            part = result[0];
            combination[combination.length - (i+1)] = alphabet.get(result[1].intValue());
        }
        nextNumber = nextNumber.add(BigInteger.ONE);
        return new String(combination);
    }
}
