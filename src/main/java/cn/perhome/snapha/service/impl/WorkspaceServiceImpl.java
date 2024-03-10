package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.dto.form.FormWorkspaceDto;
import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.mapper.WorkspaceMapper;
import cn.perhome.snapha.service.WorkspaceService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, WorkspaceEntity>
        implements WorkspaceService {

    private final WorkspaceMapper workspaceMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean snaphaCreate(FormWorkspaceDto form) {

        Long parentWid = form.getParentWid();
        if (parentWid == null) {
            form.setParentWid(0L);
        }
        Integer wsnId = this.workspaceMapper.getNextId();
        if (form.getParentWid() != 0L){
            WorkspaceEntity parentEntity = this.getById(form.getParentWid());
            String parentWsn = parentEntity.getWsn();
            form.setWsn(StringUtils.isNotBlank(parentWsn)
                    ? String.format("%s-%d", parentWsn, wsnId):String.valueOf(wsnId));
        }
        return this.save(form);
    }
}
