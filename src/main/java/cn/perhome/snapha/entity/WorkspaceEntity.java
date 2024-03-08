package cn.perhome.snapha.entity;

import cn.perhome.snapha.utils.postgres.ArrayTypeHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.io.Serializable;

@Data
@Table(value = "workspace")
public class WorkspaceEntity implements Serializable {

  @Id
  private Long wid;
  private String name;
  private Integer scheme;
  private Double area;
  private Integer weight;
  private String remark;
  private Integer status;
  private Integer deleted;
  private Long parentWid;
  private Long groupId;
  private String wsn;
  private Integer block;
  private Integer blockUnit;
  @Column(typeHandler = ArrayTypeHandler.class)
  private Integer[] fullIndex;
  private String others;

}
