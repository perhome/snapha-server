package cn.perhome.snapha.controller.api.v1.workspace;


import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormWorkspaceDto;
import cn.perhome.snapha.dto.query.QueryWorkspaceDto;
import cn.perhome.snapha.entity.WorkspaceEntity;
import cn.perhome.snapha.mapper.WorkspaceMapper;
import cn.perhome.snapha.model.Workspace;
import cn.perhome.snapha.service.WorkspaceService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.perhome.snapha.entity.table.WorkspaceEntityTableDef.WORKSPACE_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "工作区管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final WorkspaceMapper workspaceMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryWorkspaceDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create().select(WORKSPACE_ENTITY.ALL_COLUMNS)
                .where(WORKSPACE_ENTITY.PARENT_WID.eq(query.getParentWid()).when(query.getParentWid() != null))
                .orderBy(WORKSPACE_ENTITY.WEIGHT, false);
        Page<WorkspaceEntity> pageRecords       = this.workspaceMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto     responseResultDto = ResponseResultDto.success(pageRecords);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormWorkspaceDto form) {

        WorkspaceEntity entity = this.workspaceService.snaphaCreate(form);
        ResponseResultDto responseResultDto
                = entity != null? ResponseResultDto.success(true)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(entity);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{workspaceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long workspaceId, @RequestBody FormWorkspaceDto form) {

        WorkspaceEntity entity = new WorkspaceEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setWid(workspaceId);

        boolean isSuccess = this.workspaceService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{workspaceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long workspaceId) {
        boolean isSuccess = this.workspaceService.removeById(workspaceId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> treeList(QueryWorkspaceDto query) {
        String parentSn = query.getParentSn();
        if (parentSn != null) {
            WorkspaceEntity workstageEntity
                    = this.workspaceMapper.selectOneByCondition(QueryCondition.create(WORKSPACE_ENTITY.WSN, parentSn));
            query.setParentWid(workstageEntity.getWid());
        }
        else {
            query.setParentWid(0L);
        }
        List<Workspace> list = this.workspaceMapper.getTreeList(query);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "struct-tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> structTreeList(QueryWorkspaceDto query) {

        List<Workspace> list = this.workspaceMapper.getStructTreeList(query);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}
