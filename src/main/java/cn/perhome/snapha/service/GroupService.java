package cn.perhome.snapha.service;

import cn.perhome.snapha.entity.GroupEntity;
import cn.perhome.snapha.entity.UserEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;

public interface GroupService extends IService<GroupEntity> {

    List<UserEntity> getUserListByGroupId(Long groupId);
}


