package ru.sidey383.crackhash.internal.dto;

import jakarta.validation.constraints.NotBlank;

public record WorkerPartialCrackAnswer(
        @NotBlank
        String taskId
) {
}
