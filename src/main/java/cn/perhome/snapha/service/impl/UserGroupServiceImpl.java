package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.UserGroupEntity;
import cn.perhome.snapha.mapper.UserGroupMapper;
import cn.perhome.snapha.service.UserGroupService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupEntity>
        implements UserGroupService {
}
