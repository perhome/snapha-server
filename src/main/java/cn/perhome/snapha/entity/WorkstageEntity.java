package cn.perhome.snapha.entity;

import cn.perhome.snapha.utils.postgres.JsonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("workstage")
public class WorkstageEntity implements Serializable {

  @Id
  private Long     wid;
  private String   wsn;
  private String   name;
  private String   nameAbbr;
  private String   nameSpell;
  private Double   price;
  private String   workspaceAbbr;
  private Integer  weight;
  private Integer  deleted;
  private Integer  status;
  private Long     workspaceId;
  private String   remark;
  @Column(typeHandler = JsonTypeHandler.class)
  private JsonNode goods;
  private Long     parentWid;

}
