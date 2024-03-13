package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.dto.form.FormWorkspaceDto;
import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.entity.WorkspaceUserEntity;
import cn.perhome.snapha.mapper.UserGroupMapper;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.mapper.WorkspaceMapper;
import cn.perhome.snapha.mapper.WorkspaceUserMapper;
import cn.perhome.snapha.service.WorkspaceService;
import cn.perhome.snapha.utils.DateUtils;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, WorkspaceEntity>
        implements WorkspaceService {

    private final WorkspaceMapper     workspaceMapper;
    private final WorkspaceUserMapper workspaceUserMapper;
    private final UserMapper          userMapper;
    private final UserGroupMapper     userGroupMapper;

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
            this.workspaceMapper.updateIndexById(entity.getWid());
        }
        return result > 0 ? entity : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setWorkspaceUserById(WorkspaceUserEntity form) {
        WorkspaceEntity entity = this.getById(form.getWorkspaceId());
        if (entity == null) {
            return false;
        }

        Date startDate = form.getStartDate();
        if (startDate == null) {
            startDate = DateUtils.getNowDate();
        }

        Date lastUserEndDate = DateUtils.offsetsDate(startDate, -1);
        Boolean isSuccess = this.finishLastWorkspaceUserById(form.getWorkspaceId(), lastUserEndDate);
        log.info("变更：地块负责人变更。 {} 上任截止日期：{} 结果：{}", form.getWorkspaceId(), lastUserEndDate, isSuccess);

        WorkspaceUserEntity workspaceUserEntity = new WorkspaceUserEntity();
        workspaceUserEntity.setWorkspaceId(form.getWorkspaceId());
        workspaceUserEntity.setUserId(form.getUserId());
        workspaceUserEntity.setStartDate(startDate);
        int result = this.workspaceUserMapper.insert(workspaceUserEntity);
        log.info("新增：地块负责人记录追加。 {} 当前负责人：{} 结果：{}", form.getWorkspaceId(), form.getUserId(), result);

        var updateEntity = UpdateEntity.of(WorkspaceEntity.class, form.getWorkspaceId());
        updateEntity.setUserId(form.getUserId());
        result = this.workspaceMapper.update(updateEntity);
        log.info("变更：更改主地块当前负责人。 {} 当前负责人：{} 结果：{}", form.getWorkspaceId(), form.getUserId(), result);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setWorkspaceUserGroupById(WorkspaceUserEntity form) {
        WorkspaceEntity entity = this.getById(form.getWorkspaceId());
        if (entity == null) {
            return false;
        }

        Date startDate = form.getStartDate();
        if (startDate == null) {
            startDate = DateUtils.getNowDate();
        }

        var userList = this.userGroupMapper.getUserListByGroupId(form.getGroupId());
        if (userList == null || userList.size() == 0) {
            return false;
        }

        Date lastUserEndDate = DateUtils.offsetsDate(startDate, -1);
        this.finishLastWorkspaceUserById(form.getWorkspaceId(), lastUserEndDate);

        int result;
        for (var user : userList) {
            WorkspaceUserEntity workspaceUserEntity = new WorkspaceUserEntity();
            workspaceUserEntity.setWorkspaceId(form.getWorkspaceId());
            workspaceUserEntity.setUserId(user.getUid());
            workspaceUserEntity.setStartDate(startDate);
            result = this.workspaceUserMapper.insert(workspaceUserEntity);
            log.info("新增：地块负责人记录追加。 {} 当前负责人：{} 结果：{}", form.getWorkspaceId(), form.getUserId(), result);
        }

        var updateEntity = UpdateEntity.of(WorkspaceEntity.class, form.getWorkspaceId());
        updateEntity.setGroupId(form.getGroupId());
        result = this.workspaceMapper.update(updateEntity);
        log.info("变更：更改主地块当前小组。 {} 当前负责人：{} 结果：{}", form.getWorkspaceId(), form.getUserId(), result);
        return true;
    }

    private boolean finishLastWorkspaceUserById(Long workspaceId, Date endDate) {
        WorkspaceEntity entity = this.getById(workspaceId);
        if (entity == null) {
            return false;
        }
        return this.workspaceUserMapper.finishLastWorkspaceUser(workspaceId, endDate);
    }



}
