package cn.perhome.snapha.controller.api.v1.product;


import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormProductCategoryDto;
import cn.perhome.snapha.entity.ProductCategoryEntity;
import cn.perhome.snapha.mapper.ProductCategoryMapper;
import cn.perhome.snapha.model.GoodsCategory;
import cn.perhome.snapha.service.ProductCategoryService;
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

import static cn.perhome.snapha.entity.table.ProductCategoryEntityTableDef.PRODUCT_CATEGORY_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')" )
@Tag(name = "产品分类管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final ProductCategoryMapper  productCategoryMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody ProductCategoryEntity form) {
        Long parentPcid = form.getParentPcid();
        if (parentPcid == null) {
            form.setPcid(0L);
        }
      
        if (form.getParentPcid() != 0) {
            int nextId = this.productCategoryMapper.getNextId();
            ProductCategoryEntity parentEntity = this.productCategoryService.getById(form.getParentPcid());
            String              parentPcsn   = parentEntity.getPcsn();
            form.setPcsn(StringUtils.isNotBlank(parentPcsn)
                    ? String.format("%s-%d", parentPcsn, nextId) : String.valueOf(nextId));
        }
        boolean isSuccess = this.productCategoryService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess ? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @RequestMapping(value = "{productCategoryId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long productCategoryId, @RequestBody FormProductCategoryDto form) {

        ProductCategoryEntity entity = new ProductCategoryEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setPcid(productCategoryId);

        boolean           isSuccess         = this.productCategoryService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{productCategoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long productCategoryId) {
        boolean isSuccess = this.productCategoryService.removeById(productCategoryId);
        ResponseResultDto responseResultDto
                = isSuccess ? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryDto query) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(PRODUCT_CATEGORY_ENTITY.ALL_COLUMNS)
                .orderBy(PRODUCT_CATEGORY_ENTITY.NAME_ABBR, false);
        List<ProductCategoryEntity> list              = this.productCategoryService.list(queryWrapper);
        ResponseResultDto         responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> treeList(QueryDto query) {

        List<GoodsCategory> list              = this.productCategoryMapper.getTreeList(0L);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}