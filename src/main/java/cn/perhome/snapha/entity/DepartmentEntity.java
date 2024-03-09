package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.io.Serializable;

@Data
@Table("department")
public class DepartmentEntity implements Serializable {

  @Id
  private Long    did;
  private String  name;
  private Long weight;
  private Integer deleted;
  private Long    parentDid;
  private String  dsn;

}
