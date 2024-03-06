package cn.perhome.snapha.config.constant;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;

@Configuration
public class FlowableConstant implements Serializable {

    public static final int EDITOR_STATUS_CODE     = 0;
    public static final int SUCCESS_STATUS_CODE    = 100;
    public static final int FAILED_STATUS_CODE     = 40;
    public static final int PROCESSING_STATUS_CODE = 1;
    public static final int DELETED_STATUS_CODE    = 44;

    public static final String REJECT     = "拒绝";
    public static final String PASS       = "通过";
    public static final String INCREASE   = "加签";
    public static final String REDIRECT   = "转签";
    public static final String PROCESSING = "进行中";
}
