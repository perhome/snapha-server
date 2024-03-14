package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("livestock_inventory")
public class LivestockInventoryEntity implements Serializable {

  @Id
  private Long           liid;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  private Long           productId;
  private Long           workspaceId;
  private String         livestockSn;
  private Integer        originQuantity;
  private Integer        quantity;
  private Long[]         userIds;
  private String         remark;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private Integer        status;
  private String         flowableId;
  private Integer        deleted;

}
