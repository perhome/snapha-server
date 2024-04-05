package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.CfrWorkspaceEntity;
import cn.perhome.snapha.mapper.CfrWorkspaceMapper;
import cn.perhome.snapha.service.CfrWorkspaceService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CfrWorkspaceServiceImpl extends ServiceImpl<CfrWorkspaceMapper, CfrWorkspaceEntity>
        implements CfrWorkspaceService {
}

