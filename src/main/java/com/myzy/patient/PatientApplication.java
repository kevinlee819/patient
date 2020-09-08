package com.myzy.patient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * SpringBoot启动入口
 *
 * @author leekejin
 */
@Slf4j
@SpringBootApplication
public class    PatientApplication extends WebMvcConfigurationSupport {

    @Value("${uploadDir}")
    private String uploadDir;

    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File file = new File(uploadDir);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                log.error("创建上传文件夹目录失败");
            }
        }
        // 映射静态文件的访问：用于访问上传文件
        registry.addResourceHandler("/file/**").addResourceLocations(String.format("file:%s", uploadDir));
        // Swagger-ui映射
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
