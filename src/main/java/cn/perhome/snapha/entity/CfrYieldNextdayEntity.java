package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(value = "cfr_yield_nextday")
public class CfrYieldNextdayEntity implements Serializable {

  @Id
  private Long           cynid;
  private Long           workspaceId;
  private String         workspaceName;
  private String         workspaceParentName;
  private Long           productId;
  private Long           productName;
  private Integer        userId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  private Long           groupId;
  private Integer        status;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date updated;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private Integer        cfrWorkspaceId;
  private Double         level1;
  private Double         level2;
  private Double         level3;
  private Double         yield;

}
