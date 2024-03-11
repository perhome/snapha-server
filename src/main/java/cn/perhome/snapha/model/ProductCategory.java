package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.ProductCategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends ProductCategoryEntity {
    private List<ProductCategory> children;
}

