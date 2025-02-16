package ru.sidey383.crackhash.worker.permutation;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public class PermutationUtils {

    private static final BigInteger MINUS_ONE = BigInteger.ONE.negate();

    @NotNull
    public static BigInteger getCountOfFixLengthPermutation(int length, int alphabetSize) {
        if (alphabetSize <= 0 || length < 0)
            return BigInteger.ZERO;
        return BigInteger.valueOf(alphabetSize).pow(length);
    }

    @NotNull
    public static BigInteger getCountOfTotalPermutation(int length, int alphabetSize) {
        if (alphabetSize <= 0 || length < 0)
            return BigInteger.ZERO;
        if (alphabetSize == 1)
            return BigInteger.valueOf(length + 1);
        var bigAlp = BigInteger.valueOf(alphabetSize);
        var bigAlpMinOne = bigAlp.add(MINUS_ONE);
        return bigAlp.pow(length + 1).add(MINUS_ONE).divide(bigAlpMinOne);
    }

}
