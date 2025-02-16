package ru.sidey383.crackhash.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ManagerCallbackRequest(
        @NotBlank
        String taskId,
        @NotNull
        List<String> matches
) {
}
