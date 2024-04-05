package cn.perhome.snapha.model;


import cn.perhome.snapha.entity.WorkspaceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Workspace extends WorkspaceEntity {
    private Long id;
    private List<Workspace> children;
}
