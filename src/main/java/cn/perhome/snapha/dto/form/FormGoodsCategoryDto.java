package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.entity.GoodsCategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FormGoodsCategoryDto extends GoodsCategoryEntity {
    private String workspaceCode;
}
