package cn.perhome.snapha.controller.api.v1.goods;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.query.QueryGoodsDto;
import cn.perhome.snapha.entity.GoodsCheckinEntity;
import cn.perhome.snapha.entity.GoodsEntity;
import cn.perhome.snapha.mapper.GoodsCheckinMapper;
import cn.perhome.snapha.model.GoodsCheckin;
import cn.perhome.snapha.service.GoodsCheckinService;
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

import static cn.perhome.snapha.entity.table.GoodsCheckinEntityTableDef.GOODS_CHECKIN_ENTITY;
import static cn.perhome.snapha.entity.table.GoodsEntityTableDef.GOODS_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "物料")
@Slf4j
@RestController
@RequestMapping("/api/v1/goods-checkin")
@RequiredArgsConstructor
public class GoodsCheckinController {

    private final GoodsCheckinService goodsCheckinService;
    private final GoodsCheckinMapper  goodsCheckinMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody GoodsCheckin form) {

        GoodsCheckinEntity entity = new GoodsCheckinEntity();
        BeanUtils.copyProperties(form, entity);

        boolean isSuccess = this.goodsCheckinService.save(form);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        responseResultDto.setData(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{GoodsCheckinId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long GoodsCheckinId, @RequestBody GoodsCheckin form) {

        GoodsCheckin entity = new GoodsCheckin();
        BeanUtils.copyProperties(form, entity);
        entity.setGcid(GoodsCheckinId);

        boolean isSuccess = this.goodsCheckinService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{GoodsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long GoodsId) {

        boolean isSuccess = this.goodsCheckinService.removeById(GoodsId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getList(QueryGoodsDto query) {

        QueryWrapper queryWrapper = QueryWrapper.create().select(
                    GOODS_CHECKIN_ENTITY.GCID,
                    GOODS_CHECKIN_ENTITY.GCSN,
                    GOODS_CHECKIN_ENTITY.RAW_AMOUNT,
                    GOODS_CHECKIN_ENTITY.RAW_QUANTITY,
                    GOODS_CHECKIN_ENTITY.UNIT_ID,
                    GOODS_CHECKIN_ENTITY.GOODS_ID,
                    GOODS_ENTITY.NAME,
                    GOODS_ENTITY.GOODS_CATEGORY_ID
                )
                .from(GoodsCheckinEntity.class)
                .leftJoin(GoodsEntity.class)
                    .on(wrapper -> wrapper.where(GoodsCheckinEntity::getGoodsId).eq(GoodsEntity::getGid))
                .where(GOODS_CHECKIN_ENTITY.GCID.eq(query.getGcid()).when(query.getGcid() != null))
                .where(GOODS_CHECKIN_ENTITY.GCSN.eq(query.getGcsn()).when(query.getGcsn() != null))
                .where(GOODS_CHECKIN_ENTITY.DELETED.eq(query.getDeleted()).when(query.getDeleted() != null))
                .orderBy(GOODS_CHECKIN_ENTITY.CREATED, false);
        Page<GoodsCheckin> pageList          = this.goodsCheckinMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        ResponseResultDto  responseResultDto = ResponseResultDto.success(pageList);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}

