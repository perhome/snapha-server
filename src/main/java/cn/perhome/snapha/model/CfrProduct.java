package cn.perhome.snapha.model;


import cn.perhome.snapha.entity.CfrProductEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CfrProduct extends CfrProductEntity {
    public String workspaceName;
}
