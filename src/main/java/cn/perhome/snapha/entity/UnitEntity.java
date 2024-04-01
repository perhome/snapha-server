package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("unit")
public class UnitEntity implements Serializable {
    @Id
    private Long    uid;
    private String  name;
    private Long    weight;
    private Integer status;
    private String  nameAbbr;
    private Integer deleted;
    private String  symbol;
}
