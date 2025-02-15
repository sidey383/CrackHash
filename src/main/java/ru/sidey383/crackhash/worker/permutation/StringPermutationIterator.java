package ru.sidey383.crackhash.worker.permutation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class StringPermutationIterator implements Iterator<String> {

    private BigInteger currentNumber;
    private BigInteger currentMaxNumber;
    private int currentLength;
    private final int maxLength;
    private final List<Character> alphabet;
    private final BigInteger alphabetLength;

    public StringPermutationIterator(BigInteger number, int maxLength, List<Character> alphabet) {
        this.alphabet = Collections.unmodifiableList(alphabet);
        this.maxLength = maxLength;
        final int alphabetLength = alphabet.size();
        BigInteger currentNumber = number;
        BigInteger currentMaxNumber = BigInteger.ZERO;
        int currentLength = 0;
        while (currentLength <= maxLength) {
            currentMaxNumber = PermutationUtils.getCountOfFixLengthPermutation(currentLength + 1, alphabetLength);
            if (currentNumber.compareTo(currentMaxNumber) < 0) {
                currentNumber = currentNumber.add(currentMaxNumber.negate());
            } else {
                break;
            }
            currentLength++;
        }
        this.currentNumber = currentNumber;
        this.currentLength = currentLength;
        this.currentMaxNumber = currentMaxNumber;
        this.alphabetLength = BigInteger.valueOf(alphabet.size());

    }


    @Override
    public boolean hasNext() {
        return currentLength < maxLength || currentNumber.compareTo(currentMaxNumber) < 0;
    }

    @Override
    public String next() {
        currentNumber = currentNumber.add(BigInteger.ONE);
        if (currentNumber.compareTo(currentMaxNumber) < 0) {
            if (currentLength == maxLength)
                throw new NoSuchElementException("No more permutation");
            currentNumber = currentNumber.add(currentMaxNumber.negate());
            ++currentLength;
            currentMaxNumber = PermutationUtils.getCountOfFixLengthPermutation(currentLength, alphabet.size());
        }
        char[] combination = new char[currentLength];
        BigInteger part = currentNumber;
        for (int i = 0; i < combination.length; i++) {
            BigInteger[] result = part.divideAndRemainder(alphabetLength);
            part = result[0];
            combination[i] = alphabet.get(result[1].intValue());
        }
        return new String(combination);
    }
}
