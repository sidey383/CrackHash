package ru.sidey383.crackhash.core.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MessagingConfig {

    @Bean
    public FanoutExchange heartbeatExchange() {
        return new FanoutExchange("heartbeat_exchange", true, false);
    }

    @Bean
    public DirectExchange crackRequestExchange() {
        return new DirectExchange("crack_request_exchange", true, false);
    }

    @Bean
    public Queue crackRequestQueue() {
        return new Queue("crack_request_queue", true, false ,false);
    }

    @Bean
    public Binding crackRequestBinding(DirectExchange crackRequestExchange, Queue crackRequestQueue) {
        return BindingBuilder.bind(crackRequestQueue).to(crackRequestExchange).with("worker");
    }

    @Bean
    public DirectExchange crackResultExchange() {
        return new DirectExchange("crack_result_exchange", true, false);
    }

    @Bean
    public Queue crackResultQueue() {
        return new Queue("crack_result_queue", true, false ,false);
    }

    @Bean
    public Binding crackResultBinding(DirectExchange crackResultExchange, Queue crackResultQueue) {
        return BindingBuilder.bind(crackResultQueue).to(crackResultExchange).with("manager");
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("ru.sidey383.crackhash.core.dto.*", "java.util.*", "java.lang.*"));
        return converter;
    }


}
