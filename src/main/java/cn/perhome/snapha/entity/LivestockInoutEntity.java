package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(value = "livestock_inout")
public class LivestockInoutEntity implements Serializable {

  @Id
  private Long           liid;
  private String         livestockSn;
  private Long           productId;
  private Long           eventId;
  private Integer        quantity;
  private Long           userId;
  private String[]       photo;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  private String         flowableId;
  private Integer        status;
  private Integer        deleted;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date updated;
  private Long           livestockId;
  private String         remark;

}
