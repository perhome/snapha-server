package cn.perhome.snapha.security;

import cn.perhome.snapha.entity.TokenEntity;
import cn.perhome.snapha.model.UserToken;
import cn.perhome.snapha.mapper.UserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TokenHelper {

    private final UserTokenMapper userTokenMapper;

    @Autowired
    public TokenHelper(UserTokenMapper userTokenMapper) {
        this.userTokenMapper = userTokenMapper;
    }

    List<TokenEntity> findAllValidTokenByUser(Integer id) {
        return null;
    };
    Optional<UserToken> findByToken(String token) {
        var userToken = userTokenMapper.findByToken(token);
        return Optional.ofNullable(userToken);
    };
}
