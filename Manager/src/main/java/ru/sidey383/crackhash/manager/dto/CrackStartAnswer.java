package ru.sidey383.crackhash.manager.dto;

import jakarta.validation.constraints.NotBlank;

public record CrackStartAnswer(
        @NotBlank
        String requestId
) {
}
