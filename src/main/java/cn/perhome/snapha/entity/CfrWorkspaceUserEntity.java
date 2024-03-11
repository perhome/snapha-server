package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("cfr_workspace_user")
public class CfrWorkspaceUserEntity implements Serializable {

  @Id
  private Long           cwuid;
  private Long           cfrProductId;
  private Long           cfrWorkspaceId;
  private Long           userId;
  private Long           groupId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date startDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date endDate;
  private Double         attendanceRatio;
  private Long           actionId;
  private Double         yieldRatio;

}
