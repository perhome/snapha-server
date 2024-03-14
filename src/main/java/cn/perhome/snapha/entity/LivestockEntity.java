package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("livestock")
public class LivestockEntity implements Serializable {

  @Id
  private Long           lid;
  private String         lsn;
  private Long           productId;
  private Integer        quantity;
  private Integer        sex;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date startDate;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date endDate;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date birthDate;
  private Integer        status;
  private Double         weight;
  private Integer        category;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date created;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private java.util.Date updated;
  private Integer        originQuantity;
  private Long           sourceId;
  private Long           parentLid;
  private Integer        deleted;
  private Long           userId;
  private Long           workspaceId;
  private Long           inEventId;
  private Long           outEventId;

}
