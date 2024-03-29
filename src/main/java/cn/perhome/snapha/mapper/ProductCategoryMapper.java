package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.ProductCategoryEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryEntity> {

    @Select("SELECT nextval('product_category_pcid_seq')")
    int getNextId();

    List<GoodsCategory> getTreeList(Long parentPcid);
}

