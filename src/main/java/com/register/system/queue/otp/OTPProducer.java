package com.register.system.queue.otp;

import com.register.system.enums.RabbitMQ;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OTPProducer {

    RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        log.info("OTP Producer sent -> {}", message);
        rabbitTemplate.convertAndSend(
                RabbitMQ.OTP_EXCHANGE.getConfig(),
                RabbitMQ.OTP_ROUTING_KEY.getConfig(),
                message
        );
    }
}
