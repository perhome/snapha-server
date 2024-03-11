package cn.perhome.snapha.dto.query;

import cn.perhome.snapha.dto.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class QueryCommonDto extends QueryDto {
    private Long  id;
    private String keyword;
    private String start_date;
    private String end_date;

}
