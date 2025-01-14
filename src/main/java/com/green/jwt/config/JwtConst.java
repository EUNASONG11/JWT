package com.green.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt-const") // yaml에 있는 jwt-const를 담겠다는 것, Application에 @ConfigurationPropertiesScan 추가
@RequiredArgsConstructor
@ToString
public class JwtConst {
    private final String issuer;
    private final String secret;
    private final String headerSchemaName;
    private final String tokenType;
    private final int accessTokenExpiry;
    private final int refreshTokenExpiry;
    private final String refreshTokenCookieName;
    private final int refreshTokenCookieExpiry;
}
