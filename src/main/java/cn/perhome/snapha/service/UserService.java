package cn.perhome.snapha.service;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormLoginDto;
import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.dto.form.FormUserDto;
import cn.perhome.snapha.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;

public interface UserService extends IService<UserEntity> {
    ResponseResultDto register(FormRegisterDto formRegisterDto);
    ResponseResultDto snaphaRegister(FormUserDto form) throws JsonProcessingException;
    ResponseResultDto login(FormLoginDto formLoginDto);

}