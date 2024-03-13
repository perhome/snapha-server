package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.WorkspaceUserEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface WorkspaceUserMapper extends BaseMapper<WorkspaceUserEntity> {
    Boolean finishLastWorkspaceUser(Long workspaceId, Date endDate);
}

