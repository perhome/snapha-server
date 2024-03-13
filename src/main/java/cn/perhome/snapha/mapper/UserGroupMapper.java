package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.entity.UserGroupEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroupEntity> {
    List<UserEntity> getUserListByGroupId(Long groupId);
}
