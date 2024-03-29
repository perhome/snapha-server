package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryUserDto extends QueryDto {

    private Long   uid;
    private String name;
    private String usn;
    private Long   departmentId;
}

