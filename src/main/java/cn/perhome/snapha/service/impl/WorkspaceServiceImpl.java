package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.config.MyBatisFlexConfig;
import cn.perhome.snapha.dto.form.FormWorkspaceDto;
import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.mapper.WorkspaceMapper;
import cn.perhome.snapha.service.WorkspaceService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, WorkspaceEntity>
        implements WorkspaceService {

    private final WorkspaceMapper   workspaceMapper;
    private final MyBatisFlexConfig myBatisFlexConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkspaceEntity snaphaCreate(FormWorkspaceDto form) {

        Long parentWid = form.getParentWid();
        if (parentWid == null) {
            form.setParentWid(0L);
        }
        Integer wsnId = this.workspaceMapper.getNextId();
        if (form.getParentWid() != 0L){
            WorkspaceEntity parentEntity = this.getById(form.getParentWid());
            String parentWsn;
            if (parentEntity != null) {
                parentWsn = parentEntity.getWsn();
            }
            else {
                parentWsn = "SNAPHA";
            }
            form.setWsn(StringUtils.isNotBlank(parentWsn)
                    ? String.format("%s-%d", parentWsn, wsnId):String.valueOf(wsnId));
        }
        WorkspaceEntity entity = new WorkspaceEntity();
        BeanUtils.copyProperties(form, entity);

        int  result    = this.workspaceMapper.insertSelective(entity);
        if (entity.getWid() != null && !entity.getName().contains("@")) {
            this.workspaceMapper.snaphaUpdateIndex(entity);
        }
        return result > 0 ? entity : null;
    }
}
