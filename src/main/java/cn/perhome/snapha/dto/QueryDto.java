package cn.perhome.snapha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor
public class  QueryDto {
    private int     currentPage = 1;
    private int     pageSize    = 10;
    private long    totalRow    = 0L;
    private Long    userId;
    private Integer deleted;
    private String  keyword;
    private String  name;
    private String  nameAbbr;
    private String  nameSpell;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    endDate;
}
