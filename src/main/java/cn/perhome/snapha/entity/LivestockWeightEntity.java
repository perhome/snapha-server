package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("livestock_weight")
public class LivestockWeightEntity implements Serializable {

  @Id
  private Long           lwid;
  private Long           livestockId;
  private String         livestockSn;
  private Integer        quantity;
  private Double         weight;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date hitDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private java.util.Date created;
  private Long           userId;
  private Integer        weightCount;
  private JsonNode       data;
  private Long           productId;

}
