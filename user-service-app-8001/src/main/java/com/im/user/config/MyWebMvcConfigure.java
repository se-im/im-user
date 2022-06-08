package com.im.user.config;

import com.im.user.config.arguementresolver.CurrentUserMethodArgumentResolver;
import com.im.user.config.interceptor.ContextInformationInterceptor;
import com.im.user.config.interceptor.CorsInterceptor;
import com.im.user.config.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer
{


    @Autowired
    private LoginInterceptor loginInterceptor;


    @Autowired
    private ContextInformationInterceptor contextInformationInterceptor;

    @Autowired
    private CorsInterceptor corsInterceptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(contextInformationInterceptor)
                .addPathPatterns("/user/**");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/unlogin")
                .excludePathPatterns("/user/detail/token")
                .excludePathPatterns("/user/vcode/get");

        registry.addInterceptor(corsInterceptor)
                .addPathPatterns("/**");

    }


    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
//        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }
}
