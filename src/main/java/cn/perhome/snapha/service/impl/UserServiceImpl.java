package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.config.constant.SnaphaConstant;
import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormLoginDto;
import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.entity.UserEntity;

import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.AuthUser;
import cn.perhome.snapha.security.AuthenticationResponse;
import cn.perhome.snapha.security.AuthenticationService;
import cn.perhome.snapha.security.JwtService;
import cn.perhome.snapha.service.UserService;
import cn.perhome.snapha.utils.DateUtils;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
        implements UserService {

    private final UserMapper            userMapper;
    private final HttpServletRequest    request;
    private final JwtService            jwtService;
    private final AuthenticationService authenticationService;
    private final SnaphaConstant        snaphaConstant;

    public UserServiceImpl(UserMapper userMapper, HttpServletRequest request, JwtService jwtService, AuthenticationService authenticationService, SnaphaConstant snaphaConstant) {
        this.userMapper = userMapper;
        this.request = request;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.snaphaConstant = snaphaConstant;
    }

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
        int result = this.userMapper.insertSelective(userEntity);
        return ResponseResultDto.success(result);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseResultDto login(FormLoginDto formLoginDto) {
        ResponseResultDto responseResultDto;
        String passport = formLoginDto.getPassport();
        String password = formLoginDto.getPassword();

        UserEntity userEntity = this.userMapper.getByPassport(passport);
        if (userEntity == null) {
            responseResultDto = ResponseResultDto.failed(HttpStatus.FORBIDDEN.value(),"用户登陆账户不存在", formLoginDto);
            return responseResultDto;
        }

        String hexPassword = DigestUtils.md5DigestAsHex(
                password.concat(snaphaConstant.getPasswordSuffixKey()).getBytes(StandardCharsets.UTF_8));

        if (userEntity.getPassword() == null || !userEntity.getPassword().equals(hexPassword)) {
            responseResultDto = ResponseResultDto.failed(HttpStatus.FORBIDDEN.value(),"用户密码错误", formLoginDto);
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

        var authUser = AuthUser.builder()
                .passport(userEntity.getUsn())
                .id(userEntity.getUid())
                .role(userEntity.getRole())
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
