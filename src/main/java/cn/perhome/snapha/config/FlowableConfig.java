package cn.perhome.snapha.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.perhome.snapha.flowable.CustomIdmIdentityServiceImpl;

@Slf4j
@Configuration
public class FlowableConfig  implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }

    @Bean
    public EngineConfigurationConfigurer<SpringIdmEngineConfiguration> idmEngineConfigurationConfigurer() {

        return idmEngineConfiguration -> idmEngineConfiguration.setIdmIdentityService(
                new CustomIdmIdentityServiceImpl(idmEngineConfiguration)
        );
    }
}
