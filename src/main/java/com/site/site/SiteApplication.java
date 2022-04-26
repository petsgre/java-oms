package com.site.site;

import com.site.site.interceptor.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SiteApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> strings = new ArrayList<String>();
        registry.addInterceptor(new Interceptor()).addPathPatterns("/**").excludePathPatterns(strings);
        super.addInterceptors(registry);
    }
}
