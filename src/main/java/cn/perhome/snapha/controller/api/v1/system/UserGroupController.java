package cn.perhome.snapha.controller.api.v1.system;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormGroupDto;
import cn.perhome.snapha.entity.GroupEntity;
import cn.perhome.snapha.mapper.GroupMapper;
import cn.perhome.snapha.model.GoodsCategory;
import cn.perhome.snapha.service.GroupService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.perhome.snapha.entity.table.GroupEntityTableDef.GROUP_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@Tag(name = "系统组别管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/system/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final GroupService groupService;
    private final GroupMapper  groupMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormGroupDto form) {
        Long parentGid = form.getParentGid();
        if (parentGid == null) {
            form.setParentGid(0L);
        }
        Integer gcid = this.groupMapper.getNextId();
        if (form.getParentGid() != 0){
            GroupEntity parentEntity = this.groupService.getById(form.getParentGid());
            String      parentGcsn   = parentEntity.getGsn();
            form.setGsn(StringUtils.isNotBlank(parentGcsn)
                    ? String.format("%s-%d", parentGcsn, gcid):String.valueOf(gcid));
        }
        boolean isSuccess = this.groupService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long groupId, @RequestBody FormGroupDto form) {

        GroupEntity entity = new GroupEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setGid(groupId);

        boolean isSuccess = this.groupService.updateById(entity);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(404, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long groupId) {
        boolean isSuccess = this.groupService.removeById(groupId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryDto query) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(GROUP_ENTITY.ALL_COLUMNS)
                .orderBy(GROUP_ENTITY.WEIGHT, false);
        List<GroupEntity> list              = this.groupService.list(queryWrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> treeList(QueryDto query) {

        List<GoodsCategory> list              = this.groupMapper.getTreeList(0L);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}
