package cn.perhome.snapha.mapper;

import cn.perhome.snapha.model.UserToken;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

    int saveAll(List<UserToken> tokens);
    UserToken findByToken(String token);
    List<UserToken> findAllValidTokenByUser(Long userId);

}
