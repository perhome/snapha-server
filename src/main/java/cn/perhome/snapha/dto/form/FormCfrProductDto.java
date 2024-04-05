package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.entity.CfrProductEntity;
import cn.perhome.snapha.entity.WorkspaceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FormCfrProductDto extends CfrProductEntity {

    @NonNull
    private List<Long> workspaceIds;
    private List<WorkspaceEntity> workspaceList;

}
