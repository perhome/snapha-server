package cn.perhome.snapha.controller.api.v1.product;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.query.QueryProductDto;
import cn.perhome.snapha.entity.ProductEntity;
import cn.perhome.snapha.mapper.ProductMapper;
import cn.perhome.snapha.service.ProductService;
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

import static cn.perhome.snapha.entity.table.ProductEntityTableDef.PRODUCT_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "产品管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper  productMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody ProductEntity form) {

        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(form, entity);

        Long parentPid = form.getParentPid();
        if (parentPid == null) {
            form.setParentPid(0L);
        }
        form.setNameAbbr(SpellUtils.abbr(form.getName()));
        form.setNameSpell(SpellUtils.spell(form.getName()));

        boolean isSuccess = this.productService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long productId, @RequestBody ProductEntity form) {

        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setPid(productId);
        if (form.getName() != null) {
            entity.setNameAbbr(SpellUtils.abbr(form.getName()));
            entity.setNameSpell(SpellUtils.spell(form.getName()));
        }
        boolean isSuccess = this.productService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{productId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long productId) {

        boolean isSuccess = this.productService.removeById(productId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getList(QueryProductDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create().select(PRODUCT_ENTITY.ALL_COLUMNS)
                .where(PRODUCT_ENTITY.PID.eq(query.getPid()).when(query.getPid() != null))
                .where(PRODUCT_ENTITY.PSN.eq(query.getPsn()).when(query.getPsn() != null))
                .where(PRODUCT_ENTITY.DELETED.eq(query.getDeleted()).when(query.getDeleted() != null))
                .where(PRODUCT_ENTITY.CATEGORY_ID.eq(query.getCategoryId()).when(query.getCategoryId() != null))
                .where(PRODUCT_ENTITY.NAME_ABBR.eq(query.getNameAbbr()).when(query.getNameAbbr() != null))
                .orderBy(PRODUCT_ENTITY.WEIGHT, false);
        Page<ProductEntity> pageList = this.productMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(pageList);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}
