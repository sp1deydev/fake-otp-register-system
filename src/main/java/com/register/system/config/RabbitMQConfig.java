package com.register.system.config;

import com.register.system.enums.RabbitMQ;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(RabbitMQ.OTP_QUEUE.getConfig());
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQ.OTP_EXCHANGE.getConfig());
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(RabbitMQ.OTP_ROUTING_KEY);
    }
}

