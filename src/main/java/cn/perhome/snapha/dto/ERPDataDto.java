package cn.perhome.snapha.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ERPDataDto {
    private String                    start_date;
    private String                    end_date;
    private List<Map<String, Object>> list;
}
