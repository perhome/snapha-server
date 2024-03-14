package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("livestock_health")
public class LivestockHealthEntity implements Serializable {

  @Id
  private Long           lhid;
  private String         lhsn;
  private Long           livestockId;
  private Long           productId;
  private String         livestockSn;
  private Long           eventId;
  private Integer        quantity;
  private String         wsnAbbr;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private Long           userId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date updated;
  private String         flowableId;
  private Integer        deleted;

}
