package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("livestock_event")
public class LivestockEventEntity implements Serializable {

  @Id
  private Long leid;
  private String lesn;
  private String name;
  private String nameAbbr;
  private String nameSpell;
  private Long weight;
  private Integer status;
  private Long parentLeid;
  private Integer deleted;

}
