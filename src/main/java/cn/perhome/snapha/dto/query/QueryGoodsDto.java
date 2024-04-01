package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryGoodsDto extends QueryDto {

    private Long   gid;
    private Long   gcid;
    private String name;
    private String gsn;
    private String gcsn;
    private Long   goodsCategoryId;
}


