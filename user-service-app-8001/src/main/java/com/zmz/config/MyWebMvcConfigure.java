package com.zmz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer
{

//    @Value("${project.imageslocation}")
//    private String imagesLocation;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry)
//    {
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations(imagesLocation);
//    }

    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//        registry.addInterceptor(new TokenIntecepter())
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/wxLogin")
//                .excludePathPatterns("/api/unlogin");
//
//    }
}
