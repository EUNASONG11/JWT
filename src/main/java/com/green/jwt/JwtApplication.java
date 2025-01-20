package com.green.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

}
/*
Security는 필터에서 작동
필터는 (1) 아무런 작업을 하지 않고 다음 필터에게 넘긴다.
      (2) 무슨 작업을 하고 다음 필터에게 넘긴다.
      (3) 문제가 있으면 다음 필터에게 넘기지 않고 바로 예외처리 응답을 한다.

 -로그인 : 로그인 할 때 AT, RT 생성
          AT는 BODY로 리턴(응답), RT는 Cookie에 담아서 리턴(응답)
 -프론트는 AT를 받은 순간부터 모든 요청의 Header에 Authorization(인증) 키 값으로 "Bearer ${AT}"를 보내준다.
 -요청이 들어올 때마다 AT를 체크한다. 현 프로젝트 기준으로 TokenAuthenticationFilter에서 담당
  (1) Header에 Authorization 키가 있는 지 확인, 있으면 Bearer를 뺀 AT를 뽑아낸다.
    >> Token이 유효하면, Authentication 객체를 생성하고 SecurityContextHolder에 담는다. (Spring Framework Security(SFS) 미들웨어를 쓰는데 SFS가 인증 처리하는 방법)
                                                                                   , 즉 모든 요청마다 Authentication 객체가 SecurityContextHolder에 담겨 있어야 인증이 되었다고 처리한다.
    >> Token이 만료되었다면 예외를 발생시킨다. 403을 응답한다.
  (2) Header에 Authorization 키가 없었다면 아무런 작업을 안한다.
    >> SFS가 인증/인가 처리되는 URL을 사용할 수 없게 된다. (x)
    >> SFS가 인증/인가가 필요없는 URL은 사용할 수 있다. (o)

 */