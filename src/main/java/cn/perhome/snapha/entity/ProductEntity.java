package cn.perhome.snapha.entity;

import cn.perhome.snapha.utils.postgres.JsonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("product")
public class ProductEntity implements Serializable {

  @Id
  private Long     pid;
  private String   name;
  private String   nameAbbr;
  private String   nameSpell;
  private Long     categoryId;
  private Long     unitId;
  private String   workspaceSn;
  private Long     parentPid;
  private Integer  weight;
  private Integer  status;
  private Integer  deleted;
  @Column(typeHandler = JsonTypeHandler.class)
  private JsonNode othersName;
  private String   psn;
  @Column(typeHandler = JsonTypeHandler.class)
  private JsonNode specification;

}
