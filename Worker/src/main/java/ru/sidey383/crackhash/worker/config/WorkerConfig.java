package ru.sidey383.crackhash.worker.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.UUID;

@Configuration
public class WorkerConfig {

    @Bean
    public UUID workerId() {
        return UUID.randomUUID();
    }

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitTemplate timeoutRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(10000L);
        return rabbitTemplate;
    }

    @Bean
    public TaskExecutor crackTaskExecutor(@Value("${worker.concurrency:5}") Integer concurrency) {
        concurrency = Math.max(1, concurrency != null ? concurrency : 5);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1 + concurrency / 2);
        executor.setMaxPoolSize(concurrency);
        executor.setQueueCapacity(concurrency);
        return executor;
    }

}
