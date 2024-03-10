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
import java.util.Date;

@Data
@Table(value = "workspace")
public class WorkspaceEntity implements Serializable {

  @Id
  private Long      wid;
  private String    name;
  private Integer   scheme;
  private String    wsn;
  private Double    area;
  private Long      weight;
  private String    remark;
  private Integer   status;
  private Integer   deleted;
  private Long      parentWid;
  private Long      userId;
  private Long      groupId;
  private Integer   block;
  private Integer   blockUnit;
  @Column(typeHandler = ArrayTypeHandler.class)
  private Integer[] fullIndex;
  @Column(typeHandler = JsonTypeHandler.class)
  private JsonNode  others;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date      created;

}
