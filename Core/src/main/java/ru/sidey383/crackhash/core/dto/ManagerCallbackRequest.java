package ru.sidey383.crackhash.core.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ManagerCallbackRequest(
        @NotBlank
        String requestId,
        @Min(0)
        int partNumber,
        @NotNull
        List<String> answers
) {
}
