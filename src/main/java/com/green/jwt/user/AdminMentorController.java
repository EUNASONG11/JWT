package com.green.jwt.user;

import com.green.jwt.config.security.AuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("admin-mentor")
public class AdminMentorController {
    @GetMapping
    public String get() {
        log.info("admin-mentor-signedUserId: {}", AuthenticationFacade.getSignedUserId());
        return "admin-mentor";
    }
}
