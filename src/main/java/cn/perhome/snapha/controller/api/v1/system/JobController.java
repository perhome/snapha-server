package cn.perhome.snapha.controller.api.v1.system;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormDepartmentDto;
import cn.perhome.snapha.entity.DepartmentEntity;
import cn.perhome.snapha.entity.JobEntity;
import cn.perhome.snapha.service.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
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
@Tag(name = "系统工作岗位管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/system/job")
@RequiredArgsConstructor
public class JobController {
    
    private final JobService jobService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> get() {
        List<JobEntity> list = this.jobService.list();
        ResponseResultDto responseResultDto
                = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody JobEntity form) {

        boolean isSuccess = this.jobService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{jobId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> get(@PathVariable Long jobId) {

        JobEntity entity = this.jobService.getById(jobId);
        ResponseResultDto responseResultDto
                = entity != null? ResponseResultDto.success(entity)
                : ResponseResultDto.failed(404, "not found");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{jobId:\\d+}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(
            @PathVariable Long jobId, @RequestBody FormDepartmentDto form) throws JsonProcessingException {

        JobEntity entity = new JobEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setJid(jobId);
        boolean isSuccess = this.jobService.updateById(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{jobId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long jobId) {
        boolean isSuccess = this.jobService.removeById(jobId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}

