package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("goods_category")
public class GoodsCategoryEntity implements Serializable {
    @Id
    private Long    gcid;
    private String  name;
    private String  nameAbbr;
    private String  gcsn;
    private Integer weight;
    private Integer deleted;
    private Integer status;
    private String  nameSpell;
    private Long    parentGcid;
}
