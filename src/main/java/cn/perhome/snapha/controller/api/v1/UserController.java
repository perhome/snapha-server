package cn.perhome.snapha.controller.api.v1;

import cn.perhome.snapha.dto.QueryDto;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormUserDto;
import cn.perhome.snapha.dto.query.QueryUserDto;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.AuthUser;
import cn.perhome.snapha.security.Role;
import cn.perhome.snapha.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static cn.perhome.snapha.entity.table.UserEntityTableDef.USER_ENTITY;

@PreAuthorize("hasAnyRole('ADMIN')")
@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper      userMapper;
    private final UserServiceImpl userServiceImpl;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> post(@RequestBody FormUserDto form) {

        String passport = form.getUsn();
        UserEntity userEntity = this.userMapper.getByPassport(passport);
        if (userEntity != null) {
            ResponseResultDto responseResultDto = ResponseResultDto.failed(403,"用户账户已存在", form);
            return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
        }

        ResponseResultDto responseResultDto = this.userServiceImpl.snaphaRegister(form);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @Operation(summary = "认证注销登陆信息")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> logout(Authentication authentication) {
        ResponseResultDto responseResultDto;
        var authUser = (AuthUser)authentication.getPrincipal();
        log.info("用户 {}({}) 退出了系统", authUser.getId(), authUser.getUsername());
        responseResultDto = ResponseResultDto.success(null);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> list(QueryUserDto query) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(USER_ENTITY.ALL_COLUMNS)
                .where(USER_ENTITY.UID.eq(query.getUid()).when(query.getUid() != null))
                .where(USER_ENTITY.USN.eq(query.getUsn()).when(query.getUsn() != null))
                .where(USER_ENTITY.NAME.eq(query.getName()).when(query.getName() != null))
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

    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "{userId:\\d+}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> delete(Authentication authentication, @PathVariable Long userId) {
        var authUser = (AuthUser)authentication.getPrincipal();
        int isSuccess = this.userMapper.deleteById(userId);
        ResponseResultDto responseResultDto = ResponseResultDto.success(isSuccess);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getInfo(Authentication authentication) {
        var authUser = (AuthUser)authentication.getPrincipal();
        Long userId = authUser.getId();
        UserEntity entity = this.userMapper.selectOneById(userId);
        ResponseResultDto responseResultDto = ResponseResultDto.success(entity);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @RequestMapping(value = "role", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getAllRole(Authentication authentication) throws JsonProcessingException {

        var role = new HashMap<String, Object>();
        for(Role r : Role.values()) {
            role.put(r.name(), r.getPermissions());
        }
        ResponseResultDto responseResultDto = ResponseResultDto.success(role);
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

    @PreAuthorize("hasAuthority('admin:read')")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> updateInfoById(@PathVariable Long userId
            , @RequestBody FormUserDto form) {
        UserEntity wrapper = UpdateEntity.of(UserEntity.class, userId);
        wrapper.setUsn(form.getUsn());
        wrapper.setName(form.getName());
        wrapper.setJobId(form.getJobId());
        wrapper.setDepartmentId(form.getDepartmentId());
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