package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.entity.WorkspaceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FormWorkspaceDto extends WorkspaceEntity {
    private String workspaceCode;
}
