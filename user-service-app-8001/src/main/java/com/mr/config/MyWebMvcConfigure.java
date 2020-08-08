package com.mr.config;

import com.mr.config.interceptor.ContextInformationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new ContextInformationInterceptor())
                .addPathPatterns("/user/**");

    }
}
