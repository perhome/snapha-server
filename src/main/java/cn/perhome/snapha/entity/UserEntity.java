package cn.perhome.snapha.entity;


import cn.perhome.snapha.security.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("user")
public class UserEntity implements Serializable {
    @Id
    private Long           uid;
    private String         usn;
    private String         name;
    @Enumerated(EnumType.STRING)
    private String         nameAbbr;
    private String         nameSpell;
    private Integer        jobId;
    private Integer        departmentId;
    private String         jobName;
    private String         groupName;
    private Integer        groupId;
    private String         jobType;
    private String         sex;
    private Role           role = Role.USER;
    @JsonIgnore
    private String         password;
    private Integer        status;
    private Integer        deleted;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date workStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  java.util.Date lastLogin;
    private String lastLoginIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date created;
    private Boolean        isActive;


}
