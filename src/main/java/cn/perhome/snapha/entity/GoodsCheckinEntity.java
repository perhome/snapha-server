package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("goods_checkin")
public class GoodsCheckinEntity implements Serializable {

  @Id
  private Long           gcid;
  private Double         rawQuantity;
  private Double         rawAmount;
  private Double         price;
  private Double         quantity;
  private String         originSn;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date updated;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private Integer        status;
  private String         originId;
  private String         gcsn;
  private Long           userId;
  private Long           goodsId;
  private Integer        deleted;
  private Long           unitId;
  private String         unitName;
  private String         originName;

}
