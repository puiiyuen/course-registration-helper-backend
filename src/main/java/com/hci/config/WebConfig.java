package com.hci.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //photo access controller
        super.addInterceptors(registry);
        registry.addInterceptor(new UserRoleAuthorizationInterceptor()).addPathPatterns("/photo/**");
//        registry.addInterceptor(new UserRoleAuthorizationInterceptor()).addPathPatterns("/notices/pic/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //before upload to linux server change the path to "file:/opt/photo/"
        registry.addResourceHandler("/photo/**").addResourceLocations("file:D:/opt/photo/");
//        registry.addResourceHandler("/photo/**").addResourceLocations("file:/opt/photo/");
        registry.addResourceHandler("/notices/pic/**").addResourceLocations("file:D:/opt/notices/");
//        registry.addResourceHandler("/notices/pic/**").addResourceLocations("file:/opt/notices/");
        super.addResourceHandlers(registry);
    }


}
