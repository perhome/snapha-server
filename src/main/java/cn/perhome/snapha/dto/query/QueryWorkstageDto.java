package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryWorkstageDto extends QueryDto {

    private Long   wid;
    private String name;
    private String wsn;
}

