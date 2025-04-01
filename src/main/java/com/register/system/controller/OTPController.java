package com.register.system.controller;

import com.register.system.entity.OTP;
import com.register.system.service.OTPService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/otp")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OTPController {
    OTPService otpService;

    @PostMapping("/verify")
    public String verifyOTP(@RequestBody OTP otp) {
        return otpService.verifyOTP(otp.getPhoneNumber(), otp.getOtp());
    }
}
