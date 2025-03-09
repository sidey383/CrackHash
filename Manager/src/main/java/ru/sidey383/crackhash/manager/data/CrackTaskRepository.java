package ru.sidey383.crackhash.manager.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Collection;
import java.util.UUID;

public interface CrackTaskRepository extends MongoRepository<CrackTask, UUID> {


    @Query("{ 'uuid': ?0 }")
    @Update("{ $set: { 'sent': true } }")
    void markRequestAsSent(UUID uuid);

    @Query("{ 'sent': false }")
    Collection<CrackTask> getNotSentTasks();

}
