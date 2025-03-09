package ru.sidey383.crackhash.manager.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerConfig {

    @Bean
    public Queue heartbeatQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding heartbeatBinding(FanoutExchange heartbeatExchange, Queue heartbeatQueue) {
        return BindingBuilder.bind(heartbeatQueue).to(heartbeatExchange);
    }

}
