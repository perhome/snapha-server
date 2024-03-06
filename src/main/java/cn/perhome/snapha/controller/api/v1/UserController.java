package cn.perhome.snapha.controller.api.v1;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;

import cn.perhome.snapha.dto.query.QueryCommonDto;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.AuthUser;
import cn.perhome.snapha.security.AuthenticationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import cn.perhome.snapha.security.JwtService;
import java.util.*;

import static cn.perhome.snapha.entity.table.UserEntityTableDef.USER_ENTITY;

@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @Operation(summary = "认证注销登陆信息")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> logout(Authentication authentication) {
        Map<String, Object > ret = new HashMap<>();
        ResponseResultDto responseResultDto;
        var authUser = (AuthUser)authentication.getPrincipal();
        log.info("用户 {}({}) 退出了系统", authUser.getId(), authUser.getUsername());
        responseResultDto = ResponseResultDto.success(ret);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryDto query) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(USER_ENTITY.ALL_COLUMNS)
                .orderBy(USER_ENTITY.CREATED, false);
        Page<UserEntity> list;
        long totalRow = query.getTotalRow();
        if (totalRow == 0) {
            list = this.userMapper.paginate(query.getCurrentPage(), query.getPageSize(), queryWrapper);
        }
        else {
            list = this.userMapper.paginate(query.getCurrentPage(), query.getPageSize(), totalRow, queryWrapper);
        }
        ResponseResultDto responseResultDto = ResponseResultDto.success(list);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getInfoById(@PathVariable Long userId) {
        UserEntity entity = this.userMapper.selectOneById(userId);
        ResponseResultDto responseResultDto = ResponseResultDto.success(entity);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> updateInfoById(@PathVariable Long userId) {
        UserEntity wrapper = UpdateEntity.of(UserEntity.class, userId);
        int ret = this.userMapper.update(wrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(ret);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "listByXml", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> listByXml(QueryDto query) {

        QueryWrapper qw = QueryWrapper.create();
        Page<UserEntity> page = this.userMapper.xmlPaginate("getListByXml"
                , Page.of(query.getCurrentPage(), query.getPageSize()), qw);

        ResponseResultDto responseResultDto = ResponseResultDto.success(page);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "listByPageHelper", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> listByPageHelper(QueryDto query) {

        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
        List<UserEntity> list = this.userMapper.getListByPageHelper();
        PageInfo<UserEntity> info = new PageInfo<>(list);
        Page<UserEntity> page = new Page<>(list, query.getCurrentPage(), query.getPageSize(), info.getTotal());
        ResponseResultDto responseResultDto = ResponseResultDto.success(page);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}