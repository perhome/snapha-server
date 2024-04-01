package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.GoodsCheckinEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsCheckin extends GoodsCheckinEntity {
    private String name;
}

