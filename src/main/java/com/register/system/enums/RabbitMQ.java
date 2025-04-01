package com.register.system.enums;

public enum RabbitMQ {
    OTP_QUEUE("otp_queue"),
    OTP_EXCHANGE("otp_exchange"),
    OTP_ROUTING_KEY("otp_routing_key"),
    ;

    private final String config;
    RabbitMQ(String config) {
        this.config = config;
    }
    public String getConfig() {
        return this.config;
    }
}
