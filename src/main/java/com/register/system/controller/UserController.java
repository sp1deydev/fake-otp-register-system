package com.register.system.controller;

import com.register.system.dto.UpdatePasswordRequest;
import com.register.system.entity.User;
import com.register.system.repository.UserRepository;
import com.register.system.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public String createUser(@RequestBody User request) {
        return userService.createUser(request);
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestBody UpdatePasswordRequest request) {
        return userService.updatePassword(request.getPhoneNumber(), request.getPassword());
    }
}
