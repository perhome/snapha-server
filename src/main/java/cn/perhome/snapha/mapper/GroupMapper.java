package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.GroupEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
    
@Mapper
public interface GroupMapper extends BaseMapper<GroupEntity> {
    @Select("SELECT nextval('group_gid_seq')")
    int getNextId();

    List<GoodsCategory> getTreeList(Long parentGid);


}

