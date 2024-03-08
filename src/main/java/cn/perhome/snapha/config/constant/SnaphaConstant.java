package cn.perhome.snapha.config.constant;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


@Getter
@Configuration
public class SnaphaConstant implements Serializable {

    @Value("${snapha.user.password-suffix-key}")
    public String passwordSuffixKey;
}
