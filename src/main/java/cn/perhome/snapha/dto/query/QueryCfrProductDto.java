package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryCfrProductDto extends QueryDto {

    private Long           userId;
    private Long           productId;
    private Long           workspaceId;
}
