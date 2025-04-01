package com.register.system.service;

import com.register.system.entity.OTP;
import com.register.system.entity.User;
import com.register.system.repository.OTPRepository;
import com.register.system.repository.UserRepository;
import com.register.system.utils.PhoneNumberFormat;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OTPService {

    private static final int EXPIRATION_TIME_SECONDS = 180;
    private static final int MAX_ATTEMPTS = 5;
    OTPRepository otpRepository;
    UserRepository userRepository;

    public String verifyOTP(String phoneNumber, String inputOtp) {
        PhoneNumberFormat phoneNumberFormat = new PhoneNumberFormat(phoneNumber);
        Optional<OTP> optionalOtp = otpRepository.findByPhoneNumber(phoneNumberFormat.formatPhoneNumber());

        if (optionalOtp.isEmpty()) {
            throw new RuntimeException("OTP is not valid");
        }

        OTP otp = optionalOtp.get();

        if (otp.getExp().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP is expired. Please send to request to get a new OTP");
        }

        if (!otp.getOtp().equals(inputOtp)) {
            otp.setAttempts(otp.getAttempts() + 1);
            otpRepository.save(otp);

            if (otp.getAttempts() == MAX_ATTEMPTS) {
                throw new RuntimeException("You entered wrong OTP many times. Your register is revoked");
            }

            throw new RuntimeException("OTP is not match. You have " + (MAX_ATTEMPTS - otp.getAttempts()) + " times left");
        }
        User user = new User();
        user.setPhoneNumber(phoneNumberFormat.formatPhoneNumber());
        userRepository.save(user);
        return "User is verified";
    }

    @Scheduled(cron = "0 0 0 * * *")  // Thực hiện vào lúc 00:00 hàng ngày
    public void resetDailyCount() {
        LocalDate today = LocalDate.now();
        List<OTP> otpList = otpRepository.findAll();

        for (OTP otp : otpList) {
            if (otp.getExp() != null && !otp.getExp().minusSeconds(EXPIRATION_TIME_SECONDS).toLocalDate().equals(today)) {
                otp.setDailyCount(0);
                otpRepository.save(otp);
            }
        }
    }
}
