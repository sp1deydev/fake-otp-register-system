package com.register.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordFormat {
    private String password;

    public boolean isValidPassword() {
        if (password.length() < 8) {
            return false;
        }

        // Kiểm tra có ít nhất một chữ cái viết hoa, một chữ cái viết thường, một số, và một ký tự đặc biệt
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
