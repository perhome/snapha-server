package cn.perhome.snapha.controller.api.v1.farmland;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormCfrWorkspaceDto;
import cn.perhome.snapha.dto.query.QueryCfrWorkspaceDto;
import cn.perhome.snapha.entity.CfrWorkspaceEntity;
import cn.perhome.snapha.mapper.CfrWorkspaceMapper;
import cn.perhome.snapha.model.CfrWorkspace;
import cn.perhome.snapha.service.CfrWorkspaceService;
import cn.perhome.snapha.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@Tag(name = "种植管理")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farmland/cfr-workspace")
public class CfrWorkspaceController {

    private final CfrWorkspaceService cfrWorkspaceService;
    private final CfrWorkspaceMapper  cfrWorkspaceMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormCfrWorkspaceDto form) {

        boolean isSuccess = this.cfrWorkspaceService.save(form);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{cfrWorkspaceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> put(@PathVariable Long cfrWorkspaceId, @RequestBody FormCfrWorkspaceDto form) {

        var entity = new CfrWorkspaceEntity();

        BeanUtils.copyProperties(form, entity);
        entity.setCwid(cfrWorkspaceId);

        boolean isSuccess = this.cfrWorkspaceService.saveOrUpdate(entity);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to save");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{cfrWorkspaceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(@PathVariable Long cfrWorkspaceId) {
        boolean isSuccess = this.cfrWorkspaceService.removeById(cfrWorkspaceId);
        ResponseResultDto responseResultDto
                = isSuccess? ResponseResultDto.success(isSuccess)
                : ResponseResultDto.failed(500, "failed to delete");
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryCfrWorkspaceDto query) throws JsonProcessingException {

        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
        var     list = this.cfrWorkspaceMapper.getList(query);
        PageInfo<CfrWorkspace> info              = new PageInfo<>(list);
        Page<CfrWorkspace>     page
                = new Page<>(list, query.getCurrentPage(), query.getPageSize(), info.getTotal());
        log.info("page => {}", JsonUtils.generate(page));
        ResponseResultDto      responseResultDto = ResponseResultDto.success(page);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}

