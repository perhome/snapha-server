package cn.perhome.snapha.controller.api.v1.farmland;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.query.QueryWorkstageDto;
import cn.perhome.snapha.entity.WorkstageEntity;
import cn.perhome.snapha.mapper.WorkstageMapper;
import cn.perhome.snapha.service.WorkstageService;
import cn.perhome.snapha.utils.SpellUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static cn.perhome.snapha.entity.table.WorkstageEntityTableDef.WORKSTAGE_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "工序")
@Slf4j
@RestController
@RequestMapping("/api/v1/farmland/workstage")
@RequiredArgsConstructor
public class WorkstageController {

    private final WorkstageService workstageService;
    private final WorkstageMapper  workstageMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody WorkstageEntity form) {

        WorkstageEntity entity = new WorkstageEntity();
        BeanUtils.copyProperties(form, entity);

        form.setNameAbbr(SpellUtils.abbr(form.getName()));
        form.setNameSpell(SpellUtils.spell(form.getName()));

        boolean isSuccess = this.workstageService.save(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{WorkstageId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long WorkstageId, @RequestBody WorkstageEntity form) {

        WorkstageEntity entity = new WorkstageEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setWid(WorkstageId);
        if (form.getName() != null) {
            entity.setNameAbbr(SpellUtils.abbr(form.getName()));
            entity.setNameSpell(SpellUtils.spell(form.getName()));
        }
        boolean isSuccess = this.workstageService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{workstageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long workstageId) {

        boolean isSuccess = this.workstageService.removeById(workstageId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getList(QueryWorkstageDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(WORKSTAGE_ENTITY.ALL_COLUMNS)
                .where(WORKSTAGE_ENTITY.WID.eq(query.getWid()).when(query.getWid() != null))
                .orderBy(WORKSTAGE_ENTITY.WID, false)
                .orderBy(WORKSTAGE_ENTITY.WEIGHT, false);
        Page<WorkstageEntity> pageList = this.workstageMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(pageList);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}
