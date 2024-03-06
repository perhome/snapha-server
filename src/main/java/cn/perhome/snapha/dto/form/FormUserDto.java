package cn.perhome.snapha.dto.form;

import cn.perhome.snapha.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class FormUserDto extends UserEntity {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date hitDate;
}

