package cn.perhome.snapha.controller.api.v1.auth;

import cn.perhome.snapha.dto.form.FormLoginDto;
import cn.perhome.snapha.dto.ResponseResultDto;

import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.AuthUser;
import cn.perhome.snapha.security.AuthenticationResponse;
import cn.perhome.snapha.security.AuthenticationService;
import cn.perhome.snapha.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import cn.perhome.snapha.security.JwtService;

@Tag(name = "用户认证")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserMapper      userMapper;
    private final UserServiceImpl      userServiceImpl;


    @Operation(summary = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=utf-8;")
    @ResponseBody
    public ResponseEntity<ResponseResultDto> register(@RequestBody FormRegisterDto formRegisterDto) {
        ResponseResultDto responseResultDto;

        String passport = formRegisterDto.getPassport();
        UserEntity userEntity = this.userMapper.getByPassport(passport);
        if (userEntity != null) {
            responseResultDto = ResponseResultDto.failed(403,"用户账户已存在", formRegisterDto);
            return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
        }

        responseResultDto = this.userServiceImpl.register(formRegisterDto);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @Operation(summary = "登陆认证")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8;")
    @ResponseBody
    public ResponseEntity<ResponseResultDto> login(@RequestBody FormLoginDto formLoginDto) {
        ResponseResultDto responseResultDto;
        responseResultDto = this.userServiceImpl.login(formLoginDto);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    @ResponseBody
    public ResponseEntity<ResponseResultDto> refreshToken(
            HttpServletRequest request
    ) throws IOException {
        var ret = authenticationService.refreshToken(request);
        ResponseResultDto responseResultDto;
        if (ret == null) {
            responseResultDto = ResponseResultDto.failed(403, "jwt refresh token expired");
        }
        else {
            responseResultDto = ResponseResultDto.success(ret);
        }
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

}