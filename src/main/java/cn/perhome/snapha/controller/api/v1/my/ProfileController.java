package cn.perhome.snapha.controller.api.v1.my;

import cn.perhome.snapha.dto.ResponseResultDto;
import cn.perhome.snapha.dto.form.FormUserDto;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.mapper.UserMapper;
import cn.perhome.snapha.security.AuthUser;
import com.mybatisflex.core.util.UpdateEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@Tag(name = "个人用户")
@Slf4j
@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
public class ProfileController {

    private final UserMapper userMapper;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> getInfoById(Authentication authentication) {
        var authUser = (AuthUser)authentication.getPrincipal();
        UserEntity  entity = this.userMapper.selectOneById(authUser.getId());
        ResponseResultDto responseResultDto = ResponseResultDto.success(entity);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "profile/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseResultDto> updatePasswordById(Authentication authentication
            , @RequestBody FormUserDto form) {
        var authUser = (AuthUser)authentication.getPrincipal();
        UserEntity wrapper = UpdateEntity.of(UserEntity.class, authUser.getId());
        var password = form.getPassword();


        if (StringUtils.hasText(password) && password.length() > 3) {
            return new ResponseEntity<>(ResponseResultDto.failed(403, "密码不能少于4位"), HttpStatus.OK);
        }

        wrapper.setPassword(password);
        int ret = this.userMapper.update(wrapper);
        ResponseResultDto responseResultDto = ResponseResultDto.success(ret);
        return new ResponseEntity<>(responseResultDto, HttpStatus.OK);
    }
}
