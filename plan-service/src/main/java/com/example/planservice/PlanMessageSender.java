package com.example.planservice;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlanMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public PlanMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStringMessage(String msg) {
        rabbitTemplate.convertAndSend("stringQueue", msg);
    }

    public void sendObjectMessage(Plan plan) {
        rabbitTemplate.convertAndSend("objectQueue", plan);
    }
}
