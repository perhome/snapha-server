package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryWorkspaceDto extends QueryDto {

    private Long    parentWid;
    private Integer scheme;
    private String  parentSn;
}

