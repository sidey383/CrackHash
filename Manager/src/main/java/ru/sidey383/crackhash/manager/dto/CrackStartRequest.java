package ru.sidey383.crackhash.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CrackStartRequest(
        @NotBlank
        @Schema(defaultValue = "ab56b4d92b40713acc5af89985d4b786")
        String hash,
        @Min(1)
        @Schema(defaultValue = "5")
        int maxLength
) {
}
