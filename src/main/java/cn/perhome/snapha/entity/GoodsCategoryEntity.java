package cn.perhome.snapha.entity;

import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Table("goods_category")
public class GoodsCategoryEntity implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer             gcid;
    private String              name;
    private String              nameAbbr;
    private String              gcsn;
    private Integer             weight;
    private Integer             deleted;
    private Integer status;
    private String  nameSpell;
    private Integer             parentGcid;
}
