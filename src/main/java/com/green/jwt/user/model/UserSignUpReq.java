package com.green.jwt.user.model;

import com.green.jwt.config.jwt.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class UserSignUpReq {
    private final String email;
    private final String pw;
    private final String name;
    private final List<UserRole> roles = new ArrayList<>();
}
