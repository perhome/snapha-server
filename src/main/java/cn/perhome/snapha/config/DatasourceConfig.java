package cn.perhome.snapha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Bean(name = "businessDataSource")
    @Primary
    @ConfigurationProperties(prefix = "business.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}
