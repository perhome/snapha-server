package cn.perhome.snapha.entity;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("unit")
public class UnitEntity implements Serializable {
    @Id
    private Long uid;
    private String usn;
    private String name;
    private Integer weight;
    private Integer status;

}
