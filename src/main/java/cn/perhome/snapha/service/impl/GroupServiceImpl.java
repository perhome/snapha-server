package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.entity.GroupEntity;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.GroupMapper;
import cn.perhome.snapha.service.GroupService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupEntity>
        implements GroupService {

    @Override
    public List<UserEntity> getUserListByGroupId(Long groupId) {
        return null;
    }
}

