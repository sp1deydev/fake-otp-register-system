package com.register.system.repository;

import com.register.system.entity.OTP;
import com.register.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, String> {
    Optional<OTP> findByPhoneNumber(String phoneNumber);
}
