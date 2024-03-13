package cn.perhome.snapha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("user_group_history")
public class UserGroupHistoryEntity implements Serializable {

  @Id
  private Long ughid;
  private Long userId;
  private Long groupId;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
  private java.util.Date startDate;
  @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
  private java.util.Date endDate;

}
