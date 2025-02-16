package ru.sidey383.crackhash.core.dto;

import jakarta.validation.constraints.NotBlank;

public record WorkerPartialCrackAnswer(
        @NotBlank
        String taskId
) {
}
