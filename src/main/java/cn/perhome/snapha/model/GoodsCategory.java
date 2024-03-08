package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.GoodsCategoryEntity;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsCategory extends GoodsCategoryEntity {
    private List<GoodsCategory> children;
}

