package com.example.planservice;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue stringQueue() {
        return new Queue("stringQueue", false);
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("objectQueue", false);
    }
}
