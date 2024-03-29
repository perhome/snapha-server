package cn.perhome.snapha.controller.api.v1.system;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormDepartmentDto;
import cn.perhome.snapha.entity.DepartmentEntity;
import cn.perhome.snapha.mapper.DepartmentMapper;
import cn.perhome.snapha.model.Department;
import cn.perhome.snapha.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static cn.perhome.snapha.entity.table.DepartmentEntityTableDef.DEPARTMENT_ENTITY;


@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@Tag(name = "系统部门管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/system/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentMapper  departmentMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormDepartmentDto form) {
        Long parentDid = form.getParentDid();
        if (parentDid == null) {
            form.setParentDid(0L);
        }

        boolean isSuccess = this.departmentService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{departmentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> get(@PathVariable Long departmentId) {

        DepartmentEntity entity = this.departmentService.getById(departmentId);
        ResponseResultDto responseResultDto
                = entity != null? ResponseResultDto.success(entity)
                    : ResponseResultDto.failed(404, "not found");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{departmentId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long departmentId, @RequestBody FormDepartmentDto form) throws JsonProcessingException {

        DepartmentEntity entity = new DepartmentEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setDid(departmentId);
        boolean isSuccess = this.departmentService.updateById(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{departmentId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long departmentId) {
        boolean isSuccess = this.departmentService.removeById(departmentId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryDto query) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(DEPARTMENT_ENTITY.ALL_COLUMNS)
                .orderBy(DEPARTMENT_ENTITY.WEIGHT, false);
        List<DepartmentEntity> list = this.departmentService.list(queryWrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> treeList(QueryDto query) {

        List<Department>  list              = this.departmentMapper.getTreeList(0L);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}

