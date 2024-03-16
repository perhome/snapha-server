package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.entity.CfrProductEntity;
import cn.perhome.snapha.entity.WorkspaceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FromCfrProductDto extends CfrProductEntity {

    private List<WorkspaceEntity> workspace;

}
