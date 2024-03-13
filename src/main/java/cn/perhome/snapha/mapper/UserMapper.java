package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    List<UserEntity> getListByPageHelper();
    List<UserEntity> getListByXml();
    long getListByXml_COUNT();
    UserEntity getByPassport(String passport);

}
