package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.model.Workspace;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkspaceMapper extends BaseMapper<WorkspaceEntity> {
    @Select("SELECT nextval('workspace_wid_seq')")
    int getNextId();

    List<Workspace> getTreeList(@Param("parentWid") Long parentWid);

    List<Workspace> getLisByParentId(Long parentWid);

    int snaphaCreate(WorkspaceEntity entity);

    int updateIndexById(Long wid);
}

