package com.register.system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "otps")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "otp")
    String otp;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "attempts")
    Integer attempts;

    @Column(name = "daily_count")
    Integer dailyCount;

    @Column(name = "exp")
    LocalDateTime exp;

}
