package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("product_category")
public class ProductCategoryEntity implements Serializable {

  @Id
  private Long   pcid;
  private String name;
  private String pcsn;
  private Long   parentPcid;
  private String workspaceAbbr;
  private Integer deleted;
  private String nameSpell;
  private String nameAbbr;

}
