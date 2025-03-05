package ru.sidey383.crackhash.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class CrackHashManagerRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 36, max = 36)
    private String requestId;
    @PositiveOrZero
    private int partNumber;
    @PositiveOrZero
    private int partCount;
    @NotBlank
    private String hash;
    @PositiveOrZero
    private int maxLength;
    @NotNull
    private List<Character> alphabet;

}
