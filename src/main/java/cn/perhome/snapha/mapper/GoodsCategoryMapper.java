package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.GoodsCategoryEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
    
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategoryEntity> {
    @Select("SELECT nextval('goods_category_gcid_seq')")
    int getNextId();

    List<GoodsCategory> getTreeList(@Param("parentGcid") Long parentGcid);
}

