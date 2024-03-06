package cn.perhome.snapha;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.WebApplicationContextUtils;
import cn.perhome.snapha.utils.SpringContextUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@SpringBootApplication
@ComponentScan(nameGenerator = UniqueNameGenerator.class)
public class SnaphaWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SnaphaApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        SpringContextUtils.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
    }
}
