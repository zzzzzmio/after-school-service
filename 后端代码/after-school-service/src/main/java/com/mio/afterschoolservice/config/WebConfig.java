package com.mio.afterschoolservice.config;

import com.mio.afterschoolservice.interceptors.WebInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private WebInterceptors webInterceptors;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptors).excludePathPatterns("/user/login","/user/register","/file/**");
    }
}
