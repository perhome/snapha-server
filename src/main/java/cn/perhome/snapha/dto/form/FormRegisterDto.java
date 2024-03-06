package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import cn.perhome.snapha.security.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRegisterDto {
    private String passport;
    private String password;
    private Role   role;
}
