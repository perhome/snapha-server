package cn.perhome.snapha.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import cn.perhome.snapha.mapper.UserMapper;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthUserHelper {

    private final UserMapper userMapper;
    Optional<AuthUser> findByPassport(String passport) {
        AuthUser authUser = new AuthUser();
        var user          = userMapper.getByPassport(passport);
        if (user != null) {
            Set<Role> setRoles = Arrays.stream(user.getRoles())
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
            authUser = AuthUser.builder()
                    .passport(user.getUsn())
                    .id(user.getUid())
                    .roles(setRoles)
                    .build();
        }
        return Optional.of(authUser);
    };

    AuthUser save(AuthUser authUser) {
        return new AuthUser();
    };
}
