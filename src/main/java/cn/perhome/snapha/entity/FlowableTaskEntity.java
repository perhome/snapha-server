package cn.perhome.snapha.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("flowable_task")
public class FlowableTaskEntity {
    @Id
    private Long           ftid;
    private String         flowableId;
    private Long           taskId;
    private Long           userId;
    private String         userSn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date updated;
    private String         subject;
    private String         action;
    private String         processKey;
    private String         workspaceAbbr;
    private String         comment;
    private String[]       files;
    private JsonNode       data;
    private String         fromUserName;
    private Integer        fromUserId;
    private String         formUrl;
    private JsonNode       formData;
}
