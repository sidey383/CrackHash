package ru.sidey383.crackhash.manager;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class CrackTask {

    private final List<Optional<List<String>>> results;

    @SuppressWarnings("unchecked")
    public CrackTask(int partCount) {
        this.results = Collections.synchronizedList(Arrays.asList(new Optional[partCount]));
        for (int i = 0; i < partCount; i++)
            results.set(i, Optional.empty());
    }

    public boolean isComplete() {
        return results.stream().allMatch(Optional::isPresent);
    }

    public void setResult(int partNumber, @NotNull List<String> result) {
        results.set(partNumber, Optional.of(result));
    }

    @NotNull
    public List<String> getResult() {
        return results.stream()
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

}
