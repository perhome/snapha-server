package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.component.MyUserInsertListener;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormLoginDto;
import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.dto.form.FormUserDto;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.*;
import cn.perhome.snapha.service.UserService;
import cn.perhome.snapha.utils.DateUtils;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
        implements UserService {

    private final UserMapper            userMapper;
    private final HttpServletRequest    request;
    private final JwtService            jwtService;
    private final AuthenticationService authenticationService;
    private final MyUserInsertListener myUserInsertListener;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResultDto register(FormRegisterDto formRegisterDto) {
        String ip = this.request.getRemoteAddr();
        String passport = formRegisterDto.getPassport();
        String password = formRegisterDto.getPassword();
        UserEntity userEntity = new UserEntity();
        userEntity.setName(passport);
        userEntity.setUsn(passport);
        userEntity.setPassword(password);
        userEntity.setLastLogin(DateUtils.getNowDate());
        userEntity.setLastLoginIp(ip);
        userEntity.setRoles(new String[]{"USER"});
        int result = this.userMapper.insertSelective(userEntity);
        return ResponseResultDto.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResultDto snaphaRegister(FormUserDto form) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(form, userEntity);
        userEntity.setPassword(form.getPresetPassword());
        userEntity.setRoles(form.getRoles());
        int result = this.userMapper.insertSelective(userEntity);
        return ResponseResultDto.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResultDto login(FormLoginDto formLoginDto) {
        ResponseResultDto responseResultDto;
        String passport = formLoginDto.getPassport();
        String password = formLoginDto.getPassword();

        UserEntity userEntity = this.userMapper.getByPassport(passport);
        if (userEntity == null || userEntity.getPassword() == null) {
            responseResultDto
                    = ResponseResultDto.failed(HttpStatus.FORBIDDEN.value(),"用户登陆账户不存在", formLoginDto);
            return responseResultDto;
        }

        String hexPassword = this.myUserInsertListener.getHashPassword(password);
        if (!userEntity.getPassword().equals(hexPassword)) {
            responseResultDto
                    = ResponseResultDto.failed(HttpStatus.FORBIDDEN.value(),"用户密码错误", formLoginDto);
            return responseResultDto;
        }

        if (userEntity.getIsActive()
                && (formLoginDto.getForceLogin() == null || !formLoginDto.getForceLogin())) {
            return ResponseResultDto.failed(HttpStatus.ACCEPTED.value(), "用户已登陆", formLoginDto);
        }

        UserEntity entity = UpdateEntity.of(UserEntity.class, userEntity.getUid());
        entity.setIsActive(true);
        entity.setLastLogin(DateUtils.getNowDate());
        entity.setLastLoginIp(request.getRemoteAddr());
        this.userMapper.update(entity);

        Set<Role> setRoles = Arrays.stream(userEntity.getRoles())
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        var authUser = AuthUser.builder()
                .passport(userEntity.getUsn())
                .id(userEntity.getUid())
                .roles(setRoles)
                .build();
        var jwtToken = jwtService.generateToken(authUser);
        var refreshToken = jwtService.generateRefreshToken(authUser);
        authenticationService.saveUserToken(authUser, jwtToken);
        var authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken).build();
        responseResultDto = ResponseResultDto.success(authenticationResponse);
        return responseResultDto;
    }

}
