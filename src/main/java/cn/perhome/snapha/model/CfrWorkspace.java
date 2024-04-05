package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.CfrWorkspaceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CfrWorkspace extends CfrWorkspaceEntity {

    public String productName;
    public String workspaceName;
}


