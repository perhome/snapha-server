package cn.perhome.snapha.controller.api.v1;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormUnitDto;
import cn.perhome.snapha.entity.UnitEntity;
import cn.perhome.snapha.mapper.UnitMapper;
import cn.perhome.snapha.service.UnitService;
import cn.perhome.snapha.utils.SpellUtils;
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

import static cn.perhome.snapha.entity.table.UnitEntityTableDef.UNIT_ENTITY;


@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@Tag(name = "单位管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitMapper  unitMapper;
    private final UnitService unitService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormUnitDto form) {
        String name = form.getName();
        QueryCondition queryCondition = QueryCondition.create(UNIT_ENTITY.NAME, name);
        long count = this.unitService.count(queryCondition);
        if (count > 0) {
            return new ResponseEntity<>(ResponseResultDto.failed(403, "单位已存在"), HttpStatus.OK);
        }

        UnitEntity entity = new UnitEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setNameAbbr(SpellUtils.abbr(form.getName()));
        boolean isSuccess = this.unitService.save(entity);
        ResponseResultDto responseResultDto;
        if (isSuccess) {
            responseResultDto = ResponseResultDto.success(form);
        }
        else {
            responseResultDto = ResponseResultDto.failed(500, "保存失败");
        }
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{unitId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long unitId, @RequestBody FormUnitDto form) {

        UnitEntity entity = new UnitEntity();

        BeanUtils.copyProperties(form, entity);
        entity.setUid(unitId);
        entity.setNameAbbr(SpellUtils.abbr(form.getName()));
        boolean isSuccess = this.unitService.saveOrUpdate(entity);

        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{unitId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long unitId) {
        boolean isSuccess = this.unitService.removeById(unitId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getAllList(QueryDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create().select(UNIT_ENTITY.ALL_COLUMNS)
                .where(UNIT_ENTITY.DELETED.eq(0))
                .orderBy(UNIT_ENTITY.NAME_ABBR, false)
                .orderBy(UNIT_ENTITY.WEIGHT, false)
                ;
        List<UnitEntity> list = this.unitService.list(queryWrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> get(QueryDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create().select(UNIT_ENTITY.ALL_COLUMNS)
                .orderBy(UNIT_ENTITY.NAME_ABBR, false)
                .orderBy(UNIT_ENTITY.WEIGHT, false)
                ;
        Page<UnitEntity>  list
                = this.unitMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}
