package ru.sidey383.crackhash.internal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkerPartialCrackRequest(
        @NotBlank
        String hash,
        @NotNull
        @Size(min = 1)
        List<Character> alphabet,
        @Min(1)
        long length,
        @Min(1)
        long partCount,
        @Min(0)
        long partNumber
) {
}
