package cn.perhome.snapha.controller.api.v1.goods;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.query.QueryGoodsDto;
import cn.perhome.snapha.entity.GoodsEntity;
import cn.perhome.snapha.mapper.GoodsMapper;
import cn.perhome.snapha.service.GoodsService;
import cn.perhome.snapha.utils.SpellUtils;
import com.mybatisflex.core.paginate.Page;
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

import java.util.function.Consumer;

import static cn.perhome.snapha.entity.table.GoodsEntityTableDef.GOODS_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "物料")
@Slf4j
@RestController
@RequestMapping("/api/v1/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsMapper  goodsMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody GoodsEntity form) {

        GoodsEntity entity = new GoodsEntity();
        BeanUtils.copyProperties(form, entity);

        form.setNameAbbr(SpellUtils.abbr(form.getName()));
        form.setNameSpell(SpellUtils.spell(form.getName()));

        boolean isSuccess = this.goodsService.save(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{GoodsId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long GoodsId, @RequestBody GoodsEntity form) {

        GoodsEntity entity = new GoodsEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setGid(GoodsId);
        if (form.getName() != null) {
            entity.setNameAbbr(SpellUtils.abbr(form.getName()));
            entity.setNameSpell(SpellUtils.spell(form.getName()));
        }
        boolean isSuccess = this.goodsService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{GoodsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long GoodsId) {

        boolean isSuccess = this.goodsService.removeById(GoodsId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getList(QueryGoodsDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(GOODS_ENTITY.ALL_COLUMNS)
                .where(GOODS_ENTITY.GID.eq(query.getGid()).when(query.getGid() != null))
                .and(GOODS_ENTITY.GSN.eq(query.getGsn()).when(query.getGsn() != null))
                .and(GOODS_ENTITY.DELETED.eq(query.getDeleted()).when(query.getDeleted() != null))
                .and(GOODS_ENTITY.GOODS_CATEGORY_ID.eq(query.getGoodsCategoryId()).when(query.getGoodsCategoryId() != null))
                .and(GOODS_ENTITY.NAME_ABBR.eq(query.getNameAbbr()).when(query.getNameAbbr() != null))
                .orderBy(GOODS_ENTITY.WEIGHT, false);
        if (query.getKeyword() != null) {
            queryWrapper.and(
                    (Consumer<QueryWrapper>) wrapper -> wrapper
                    .or(GOODS_ENTITY.NAME.likeLeft(query.getKeyword()))
                    .or(GOODS_ENTITY.NAME_ABBR.likeLeft(query.getKeyword()))
                    .or(GOODS_ENTITY.NAME_SPELL.likeLeft(query.getKeyword()))
                    .or(GOODS_ENTITY.GSN.eq(query.getKeyword()))
                    .or(GOODS_ENTITY.GID.eq(StringUtils.isNumeric(query.getKeyword())?Long.parseLong(query.getKeyword()):0L).when(StringUtils.isNumeric(query.getKeyword())))
            );
        }
        Page<GoodsEntity> pageList = this.goodsMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto   responseResultDto = ResponseResultDto.success(pageList);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}

