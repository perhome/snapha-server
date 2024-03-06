package cn.perhome.snapha.service;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormLoginDto;
import cn.perhome.snapha.dto.form.FormRegisterDto;
import cn.perhome.snapha.entity.UserEntity;
import com.mybatisflex.core.service.IService;

public interface UserService extends IService<UserEntity> {
    ResponseResultDto register(FormRegisterDto formRegisterDto);
    ResponseResultDto login(FormLoginDto formLoginDto);
}