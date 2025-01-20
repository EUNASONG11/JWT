package com.green.jwt.config.security;

import com.green.jwt.config.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationFacade {
    public static long getSignedUserId(){
        return ((JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSignedUserId();
    }
}
