package cn.perhome.snapha.mapper;
import cn.perhome.snapha.entity.GroupEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;
    
@Mapper
public interface GroupMapper extends BaseMapper<GroupEntity> {
    @Select("SELECT nextval('group_gid_seq')")
    int getNextId();

    List<GoodsCategory> getTreeList(@Param("parentGid") Integer parentGid);
}

