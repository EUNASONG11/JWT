package com.green.jwt.user;

import com.green.jwt.user.model.UserSelOne;
import com.green.jwt.user.model.UserSignInReq;
import com.green.jwt.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insUser(UserSignUpReq req);
    int insUserRole(UserSignUpReq req);
    // Optional은 Null 체크를 위한 if문 없이 NPE(NullPointerException)이 발생하지 않는 간결하고 안전한 코드를 작성하는 것이 가능
    Optional<UserSelOne> selUserWithRoles(UserSignInReq req);
}
