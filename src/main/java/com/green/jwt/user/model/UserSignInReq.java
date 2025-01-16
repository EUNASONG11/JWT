package com.green.jwt.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserSignInReq {
    @Schema(title="아이디", example = "ddd@naver.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(title="비밀번호", example = "1212", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pw;
}
