package cn.perhome.snapha.config;

import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class FlowableDatasourceConfig {
    private final DataSource dataSource;

    private final PlatformTransactionManager transactionManager;

    public FlowableDatasourceConfig(@Qualifier("flowableDataSource") DataSource dataSource,
                                    @Qualifier("flowableTransactionManager") PlatformTransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
    }

    @Bean
    public EngineConfigurationConfigurer<SpringAppEngineConfiguration> engineConfigurer() {
        return configuration -> {
            configuration.setDataSource(dataSource);
            configuration.setTransactionManager(transactionManager);
//            configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        };
    }
}
