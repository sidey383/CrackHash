package ru.sidey383.crackhash.manager.dto;

import java.util.List;

public record CrackStatusAnswer(
    CrackStatus status,
    List<String> data
) {
}
