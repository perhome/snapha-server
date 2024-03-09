package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.io.Serializable;

@Data
@Table("job")
public class JobEntity implements Serializable {

  @Id
  private Long jid;
  private String name;
  private Long departmentId;
  private Integer deleted;

}
