package com.green.jwt.config.security;

import com.green.jwt.config.jwt.TokenAuthenticationFilter;
import com.green.jwt.config.jwt.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //빈등록, Configuration을 쓰면 보통 빈 등록 메소드가 포함되어 있을 가능성이 높다
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean // Spring이 메소드 호출을 하고 리턴한 객체의 주소값을 관리한다.(빈 등록)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session을 security가 사용하지 않는다
                .httpBasic(h -> h.disable()) // SSR(Server Side Rendering)이 아니다. 화면을 만들지 않을 것이기 때문에 비활성화 시킨다. 시큐리티 로그인 창이 나타나지 않을 것이다.
                .formLogin(form -> form.disable()) // SSR(Server Side Rendering)이 아니다. 폼로그인 기능 자체를 비활성화
                .csrf(csrf -> csrf.disable()) // SSR(Server Side Rendering)이 아니다. 보안 관련 SSR이 아니면 보안 이슈가 없기 때문에 기능을 끈다.
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(req ->
                                     req.requestMatchers("api/admin").hasRole(UserRole.ADMIN.name()) //인가 하나 처리
                                        .requestMatchers("api/mentor").hasRole(UserRole.MENTOR.name())
                                        .requestMatchers("api/admin-mentor").hasAnyRole(UserRole.ADMIN.name(), UserRole.MENTOR.name()) //인가 여러 개 처리
                                        .requestMatchers("/api/admin", "/api/mentor", "/api/admin-mentor", "/api/user").authenticated()

                                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
