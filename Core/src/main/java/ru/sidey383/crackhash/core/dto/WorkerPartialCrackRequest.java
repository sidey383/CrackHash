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
        String requestId,
        @Min(0)
        int partNumber,
        @Min(1)
        int partCount,
        @NotBlank
        String hash,
        @Min(1)
        int maxLength,
        @NotNull
        @Size(min = 1)
        Collection<Character> alphabet
) {
}
