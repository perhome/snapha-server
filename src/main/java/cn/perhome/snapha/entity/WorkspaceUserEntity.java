package cn.perhome.snapha.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("workspace_user")
public class WorkspaceUserEntity implements Serializable {

  @Id
  private Long           wuid;
  private Long           workspaceId;
  private Long           userId;
  private Long        groupId;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date startDate;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date endDate;
  private Integer        deleted;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private String         workspaceAbbr;

}
