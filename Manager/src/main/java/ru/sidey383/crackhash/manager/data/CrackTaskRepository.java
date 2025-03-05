package ru.sidey383.crackhash.manager.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CrackTaskRepository extends MongoRepository<CrackTask, UUID> {
}
