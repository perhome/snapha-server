package cn.perhome.snapha.mapper;

import cn.perhome.snapha.dto.query.QueryCfrWorkspaceDto;
import cn.perhome.snapha.entity.CfrWorkspaceEntity;
import cn.perhome.snapha.model.CfrWorkspace;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CfrWorkspaceMapper extends BaseMapper<CfrWorkspaceEntity> {

    List<CfrWorkspace> getList(QueryCfrWorkspaceDto query);
}


