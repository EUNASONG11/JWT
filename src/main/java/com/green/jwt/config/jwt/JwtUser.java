package com.green.jwt.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.lang.model.util.SimpleElementVisitor6;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

//동일성 - 주소값이 같으냐?
//동등성 - 주소값은 다른 존재끼리 같은 값을 가지고 있음

@Getter
@EqualsAndHashCode //equals, hashCode 메소드 오버라이딩
@RequiredArgsConstructor
public class JwtUser  implements UserDetails {
    private final long signedUserId;
    private final List<UserRole> roles; // 인가(권한)처리 때 사용, 여러 권한 부여를 위해 List, "ROLE_이름" > ROLE_USER, ROLE_ADMIN


    // 리턴 타입이 collection인데 collection에 방 하나하나의 타입은 <> 지정을 한다.
    // <?> fh gkaus Object가 되기 때문에 모든 타입이 허용이 된다.
    // <? extends GrantedAuthority>는 지정 타입이 꼭 GrantedAuthority 포함, GrantedAuthority를 상속 받은 객체만 가능하도록 제한을 거는 것

    // <? super GrantedAuthority>는 지정 타입이 꼭 GrantedAuthority 포함, GrantedAuthority의 부모 객체만 가능하도록 제한을 거는 것
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    /*
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
        와

        return roles.stream().map(SimpleGrantedAuthority::new).toList();
        가 동일
     */

        return roles.stream() //List<String>을 Stream<List<String>>으로 변환
//          Function fn = new Function<String, SimpleGrantedAuthority>() {
//             @Override
//             public SimpleGrantedAuthority apply(String str) {
//                 return new SimpleGrantedAuthority(str);
//             }
//          };
//                      .map(fn)

                       //SimpleGrantedAuthority::new 은 메소드 참조라고 호칭
                       //.map(item -> new SimpleGrantedAuthority(item))와 같은 내용이다.
                       //.map(item -> { return new SimpleGrantedAuthority(item); }) 리턴 메소드 였을 때 {}을 쓰면 return 사용해야 함. 즉, {}를 생략하면 return 자동으로 들어감
                    .map(item -> new SimpleGrantedAuthority(item.name())) //map은 똑같은 size의 stream을 만든다. Stream<List<SimpleGrantedAuthority>>으로 변환, map에 Function을 implements한 객체 주소값을 보내고 있다.
                    .toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
