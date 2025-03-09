package ru.sidey383.crackhash.manager.dto;

import java.util.UUID;

public record ErrorAnswer(String status, String reason, String description, UUID errorUUID) {



}
