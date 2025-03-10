package ru.sidey383.crackhash.manager.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.IntStream;

@Getter
@Document(collection = "crack_tasks")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrackTask {
    @Id
    private UUID uuid = UUID.randomUUID();

    private String hash;

    private int maxLength;

    private Map<Integer, List<String>> results  = new HashMap<>();

    private Set<Character> alphabet = Set.of();

    private Integer partCount;

    private Boolean sent = false;

    public CrackTask(String hash, int maxLength, Collection<Character> alphabet, int partCount) {
        this.partCount = partCount;
        this.alphabet = Set.copyOf(alphabet);
        this.hash = hash;
        this.maxLength = maxLength;
    }

    public boolean isComplete() {
        return IntStream.range(0, partCount).allMatch(i -> Objects.nonNull(results.get(i)));
    }

    public void setResult(int partNumber, @NotNull List<String> result) {
        results.put(partNumber, result);
    }


    @NotNull
    public List<String> getResult() {
        return results.values().stream()
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

}
