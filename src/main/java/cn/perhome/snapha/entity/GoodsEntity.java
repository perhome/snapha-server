package cn.perhome.snapha.entity;

import cn.perhome.snapha.utils.postgres.ArrayTypeHandler;
import cn.perhome.snapha.utils.postgres.JsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("goods")
public class GoodsEntity implements Serializable {

  @Id
  private Long           gid;
  private String         name;
  private String         nameAbbr;
  private String         nameSpell;
  private Long           goodsCategoryId;
  private Long           unitId;
  @Column(typeHandler = ArrayTypeHandler.class)
  private Long[]         useUnit;
  private Integer        status;
  private Integer        deleted;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private String         remark;
  private Integer        weight;
  private String         workspaceAbbr;
  private String   gsn;
  @Column(typeHandler = JsonTypeHandler.class)
  private JsonNode othersName;

}
