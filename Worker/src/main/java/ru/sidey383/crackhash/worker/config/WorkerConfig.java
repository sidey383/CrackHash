package ru.sidey383.crackhash.worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class WorkerConfig {

    @Bean
    public UUID workerId() {
        return UUID.randomUUID();
    }

}
