package cn.perhome.snapha.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormLoginDto {
    private String password;
    private String passport;
    private Boolean forceLogin;

}
