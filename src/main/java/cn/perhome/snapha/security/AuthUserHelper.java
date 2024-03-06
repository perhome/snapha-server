package cn.perhome.snapha.security;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import cn.perhome.snapha.mapper.UserMapper;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserHelper {

    private final UserMapper userMapper;
    Optional<AuthUser> findByPassport(String passport) {
        AuthUser authUser = new AuthUser();
        var user          = userMapper.getByPassport(passport);
        if (user != null) {
            authUser = AuthUser.builder()
                    .passport(user.getUsn())
                    .id(user.getUid())
                    .role(user.getRole())
                    .build();
        }
        return Optional.of(authUser);
    };

    AuthUser save(AuthUser authUser) {
        return new AuthUser();
    };
}
