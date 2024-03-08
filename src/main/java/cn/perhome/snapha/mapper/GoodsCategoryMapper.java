package cn.perhome.snapha.mapper;
import cn.perhome.snapha.entity.GoodsCategoryEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;
    
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategoryEntity> {
    @Select("SELECT nextval('goods_category_gcid_seq')")
    int getNextId();

    List<GoodsCategory> getTreeList(@Param("parentGcid") Integer parentGcid);
}

