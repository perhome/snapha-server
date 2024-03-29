package cn.perhome.snapha.entity;



import cn.perhome.snapha.utils.postgres.ArrayTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(value = "user")
public class UserEntity implements Serializable {
    @Id
    private Long           uid;
    private String         usn;
    private String         name;
    @Enumerated(EnumType.STRING)
    private String         nameAbbr;
    private String         nameSpell;
    private Long        jobId;
    private Long        departmentId;
    private String         jobName;
    private String         groupName;
    private Long        groupId;
    private String         jobType;
    private String         sex;
    @Column(typeHandler = ArrayTypeHandler.class)
    private String[]       roles;
    @JsonIgnore
    private String         password;
    private String         presetPassword;
    private Integer        status;
    private Integer        deleted;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date workStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date lastLogin;
    private String         lastLoginIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date created;
    private Boolean        isActive = false;


}
