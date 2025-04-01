package com.register.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneNumberFormat {
    private String phoneNumber;

    public String formatPhoneNumber() {
        phoneNumber = phoneNumber.trim();

        if (phoneNumber.startsWith("+84")) {
            phoneNumber = "84" + phoneNumber.substring(3);
        }

        if (phoneNumber.startsWith("0")) {
            phoneNumber = "84" + phoneNumber.substring(1);
        }

        if (phoneNumber.startsWith("84") && phoneNumber.length() != 11) {
            throw new RuntimeException("Phone number with country code '84' must have 11 digits");
        }
        if (phoneNumber.startsWith("84")) {
            return phoneNumber;
        }
        throw new RuntimeException("Phone number format is not valid");
    }
}
