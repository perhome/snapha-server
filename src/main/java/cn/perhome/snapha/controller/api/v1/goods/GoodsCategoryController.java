package cn.perhome.snapha.controller.api.v1.goods;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormGoodsCategoryDto;
import cn.perhome.snapha.dto.form.FormUnitDto;
import cn.perhome.snapha.entity.GoodsCategoryEntity;
import cn.perhome.snapha.entity.UnitEntity;
import cn.perhome.snapha.mapper.GoodsCategoryMapper;
import cn.perhome.snapha.model.GoodsCategory;
import cn.perhome.snapha.service.GoodsCategoryService;
import com.mybatisflex.core.query.QueryCondition;
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

import static cn.perhome.snapha.entity.table.GoodsCategoryEntityTableDef.GOODS_CATEGORY_ENTITY;
import static cn.perhome.snapha.entity.table.UnitEntityTableDef.UNIT_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "物料分类管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/goods-category")
@RequiredArgsConstructor
public class GoodsCategoryController {

    private final GoodsCategoryService goodsCategoryService;
    private final GoodsCategoryMapper  goodsCategoryMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormGoodsCategoryDto form) {
        Integer parentGcid = form.getParentGcid();
        if (parentGcid == null) {
            form.setGcid(0);
        }
        Integer gcid = this.goodsCategoryMapper.getNextId();
        if (form.getParentGcid() != 0){
            GoodsCategoryEntity parentEntity = this.goodsCategoryService.getById(form.getParentGcid());
            String parentGcsn = parentEntity.getGcsn();
            form.setGcsn(StringUtils.isNotBlank(parentGcsn)
                    ? String.format("%s-%d", parentGcsn, gcid):String.valueOf(gcid));
        }
        boolean isSuccess = this.goodsCategoryService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{goodsCategoryId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Integer goodsCategoryId, @RequestBody FormGoodsCategoryDto form) {

        GoodsCategoryEntity entity = new GoodsCategoryEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setGcid(goodsCategoryId);

        boolean isSuccess = this.goodsCategoryService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{goodsCategoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Integer goodsCategoryId) {
        boolean isSuccess = this.goodsCategoryService.removeById(goodsCategoryId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryDto query) {
//        QueryCondition queryCondition = QueryCondition.create(UNIT_ENTITY.DELETED, 0);
        QueryWrapper queryWrapper = QueryWrapper.create().select(GOODS_CATEGORY_ENTITY.ALL_COLUMNS)
                .orderBy(GOODS_CATEGORY_ENTITY.WEIGHT, false);
        List<GoodsCategoryEntity> list = this.goodsCategoryService.list(queryWrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> treeList(QueryDto query) {

        List<GoodsCategory> list  = this.goodsCategoryMapper.getTreeList(0);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}

