package ru.sidey383.crackhash.worker.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public FanoutExchange heartbeatExchange() {
        return new FanoutExchange("heartbeat_exchange", true, false);
    }

}
