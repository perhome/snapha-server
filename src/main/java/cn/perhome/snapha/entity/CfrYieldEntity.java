package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("cfr_yield")
public class CfrYieldEntity implements Serializable {

  @Id
  private Long           cyid;
  private Long           productId;
  private String         productName;
  private String         workspaceName;
  private String         workspaceParentName;
  private Double         level1;
  private Double         level2;
  private Double         level3;
  private Double         yield;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  private Integer        status;
  private Long           userId;
  private Long           groupId;
  private Long           authUserId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private String         photo;
  private Double         entryYield;
  private String         flowableId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date updated;
  private String         to;
  private String         containerType;
  private Integer        containerNumber;
  private Integer        cfrWorkspaceId;

}
