package cn.perhome.snapha.service;


import cn.perhome.snapha.dto.form.FormWorkspaceDto;
import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.entity.WorkspaceUserEntity;
import com.mybatisflex.core.service.IService;

public interface WorkspaceService extends IService<WorkspaceEntity> {
    WorkspaceEntity snaphaCreate(FormWorkspaceDto form);

    boolean setWorkspaceUserById(WorkspaceUserEntity form);

    boolean setWorkspaceUserGroupById(WorkspaceUserEntity form);
}
