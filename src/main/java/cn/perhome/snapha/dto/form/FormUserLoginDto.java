package cn.perhome.snapha.dto.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class FormUserLoginDto {
    private String userCode;
    @NotNull
    private String passport;
    @NotNull
    private String password;
    private String deviceId = "web";
    private int expiry = 24 * 3600;
}
