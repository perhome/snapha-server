package cn.perhome.snapha.config.constant;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

@Getter
@Configuration
@PropertySource("classpath:/constant/workspace.properties")
public class WorkspaceConstant implements Serializable {
    @Value("${workspace.farmland-code}")
    private String farmlandCode;
    @Value("${workspace.livestock-code}")
    private String livestockCode;
    @Value("${workspace.synthesis-code}")
    private String synthesisCode;
    @Value("${workspace.warehouse-code}")
    private String warehouseCode;
}

