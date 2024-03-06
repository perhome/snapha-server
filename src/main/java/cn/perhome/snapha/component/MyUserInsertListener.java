package cn.perhome.snapha.component;

import cn.perhome.snapha.config.constant.SnaphaConstant;
import cn.perhome.snapha.entity.UserEntity;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class MyUserInsertListener implements InsertListener, UpdateListener {

    private final SnaphaConstant snaphaConstant;

    @Override
    public void onInsert(Object entity) {
        UserEntity user = (UserEntity)entity;
        var password = user.getPassword();
        if (password != null) {
            user.setPassword(getHashPassword(password));
        }
    }

    @Override
    public void onUpdate(Object entity) {
        UserEntity user = (UserEntity)entity;
        var password = user.getPassword();
        if (password != null) {
            user.setPassword(getHashPassword(password));
        }
    }

    private String getHashPassword(String password) {
        String suffixKey = snaphaConstant.getPasswordSuffixKey();
        password = password.concat(suffixKey);
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

}
