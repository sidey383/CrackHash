package ru.sidey383.crackhash.worker.permutation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class IterableWordTest {

    private static final List<String> abcPermutations = List.of(
            "",
            "a", "b", "c",
            "aa", "ab", "ac",
            "ba", "bb", "bc",
            "ca", "cb", "cc",
            "aaa", "aab", "aac",
            "aba", "abb", "abc",
            "aca", "acb", "acc",
            "baa", "bab", "bac",
            "bba", "bbb", "bbc",
            "bca", "bcb", "bcc",
            "caa", "cab", "cac",
            "cba", "cbb", "cbc",
            "cca", "ccb", "ccc"
    );
    private static final List<Character> abcAlphabet = List.of('a', 'b', 'c');

    @ParameterizedTest
    @MethodSource
    public void abcTest(long start) {
        IterableWords permutations = new IterableWords(3, abcAlphabet);
        permutations.skip(BigInteger.valueOf(start));
        BigInteger totalCount = permutations.getTotalWordNumber();
        BigInteger availableCount = permutations.getAvailableWordNumber();

        List<String> expected = abcPermutations.stream().skip(start).toList();

        assertThat(permutations).containsExactlyElementsOf(expected);
        assertThat(availableCount.intValue()).isEqualTo(expected.size());
        assertThat(totalCount.intValue()).isEqualTo(abcPermutations.size());
    }

    public static Stream<Arguments> abcTest() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(5),
                Arguments.of(10)
        );
    }

}
