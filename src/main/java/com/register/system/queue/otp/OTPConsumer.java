package com.register.system.queue.otp;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OTPConsumer {
    @RabbitListener(queues = {"otp_queue"})
    public void consume(String message) {
        log.info("OTP RabbitMQ consume: {}", message);
    }
}
