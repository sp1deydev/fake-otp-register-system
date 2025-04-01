package com.register.system.service;

import com.register.system.entity.OTP;
import com.register.system.entity.User;
import com.register.system.queue.otp.OTPProducer;
import com.register.system.repository.OTPRepository;
import com.register.system.repository.UserRepository;
import com.register.system.utils.PasswordFormat;
import com.register.system.utils.PhoneNumberFormat;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final int EXPIRATION_TIME_SECONDS = 180;
    private static final int RESEND_DELAY_SECONDS = 120;
    UserRepository userRepository;
    OTPRepository otpRepository;
    OTPProducer otpProducer;

    public String createUser(User request) {
        PhoneNumberFormat phoneNumberFormat = new PhoneNumberFormat(request.getPhoneNumber());
        if(userRepository.findByPhoneNumber(phoneNumberFormat.formatPhoneNumber()).isPresent()) {
            throw new RuntimeException("User is existed");
        }

        LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(EXPIRATION_TIME_SECONDS);
        Random random = new Random();
        int number = random.nextInt(1000000);

        OTP otp = new OTP();
        if(otpRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            otp = otpRepository.findByPhoneNumber(request.getPhoneNumber()).get();
            if (otp.getExp() != null && otp.getExp()
                    .minusSeconds(EXPIRATION_TIME_SECONDS)
                    .plusSeconds(RESEND_DELAY_SECONDS)
                    .isAfter(LocalDateTime.now())
            ) {
                throw new RuntimeException("Please wait 120 seconds before requesting OTP again");
            }

            if (otp.getDailyCount() == 5) {
                throw new RuntimeException("Daily OTP limit reached for this phone number");
            }
            otp.setDailyCount(otp.getDailyCount() + 1);
        }
        else {
            otp.setDailyCount(1);
        }
        otp.setPhoneNumber(phoneNumberFormat.formatPhoneNumber());
        String otpValue = String.format("%06d", number);
        otp.setOtp(otpValue); // Đảm bảo có đúng 6 chữ số, kể cả số 0 ở đầu
        otp.setAttempts(0);
        otp.setExp(expiryTime);
        otpRepository.save(otp);
        otpProducer.sendMessage(otpValue);
        return "Please verify OTP code";
    }

    public String updatePassword(String phoneNumber, String password) {
        PhoneNumberFormat phoneNumberFormat = new PhoneNumberFormat(phoneNumber);
        PasswordFormat passwordFormat = new PasswordFormat(password);
        if(userRepository.findByPhoneNumber(phoneNumberFormat.formatPhoneNumber()).isEmpty()) {
            throw new RuntimeException("Phone number is not registered");
        }
        if(!passwordFormat.isValidPassword()) {
            throw new RuntimeException("Password format is not valid. Please enter another password");
        }
        User user = userRepository.findByPhoneNumber(phoneNumberFormat.getPhoneNumber()).get();
        user.setPassword(password);
        userRepository.save(user);
        return "Password is updated";
    }
}
