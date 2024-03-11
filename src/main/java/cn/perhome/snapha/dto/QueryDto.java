package cn.perhome.snapha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class  QueryDto {
    private int    currentPage = 1;
    private int    pageSize    = 10;
    private long   totalRow    = 0L;
    private Long   id;
    private String keyword;
    private String start_date;
    private String end_date;
}
