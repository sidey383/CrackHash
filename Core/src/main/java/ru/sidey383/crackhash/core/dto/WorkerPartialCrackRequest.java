package ru.sidey383.crackhash.core.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Collection;

@Builder
public record WorkerPartialCrackRequest(
        @NotBlank
        String hash,
        @NotNull
        @Size(min = 1)
        Collection<Character> alphabet,
        @Min(1)
        int length,
        @Min(1)
        long partCount,
        @Min(0)
        long partNumber
) {
}
