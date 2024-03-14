package cn.perhome.snapha.controller.api.v1.farmland;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FromCfrProductDto;
import cn.perhome.snapha.dto.query.QueryCfrProductDto;
import cn.perhome.snapha.entity.CfrProductEntity;
import cn.perhome.snapha.mapper.CfrProductMapper;
import cn.perhome.snapha.service.CfrProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "种植管理")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cfr-product")
public class CfrProductController {

    private final CfrProductService cfrProductService;
    private final CfrProductMapper  cfrProductMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FromCfrProductDto form) {

        boolean isSuccess = this.cfrProductService.save(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(500, "failed to save");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{cfrProductId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long cfrProductId, @RequestBody FromCfrProductDto form) {

        var entity = new CfrProductEntity();

        BeanUtils.copyProperties(form, entity);
        entity.setCpid(cfrProductId);

        boolean isSuccess = this.cfrProductService.saveOrUpdate(entity);

        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(500, "failed to save");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{cfrProductId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long cfrProductId) {
        boolean isSuccess = this.cfrProductService.removeById(cfrProductId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                    : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryCfrProductDto query) {

        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
        List<CfrProductEntity>     list = this.cfrProductMapper.getList(query);
        PageInfo<CfrProductEntity> info = new PageInfo<>(list);
        Page<CfrProductEntity>     page = new Page<>(list, query.getCurrentPage(), query.getPageSize(), info.getTotal());
        ResponseResultDto responseResultDto = ResponseResultDto.success(page);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}
