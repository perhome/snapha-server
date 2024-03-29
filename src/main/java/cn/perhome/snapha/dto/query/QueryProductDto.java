package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryProductDto extends QueryDto {

    private Long   pid;
    private String name;
    private String psn;
    private Long categoryId;
}

