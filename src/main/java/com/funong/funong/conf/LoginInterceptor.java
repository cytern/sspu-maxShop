package com.funong.funong.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: cytern
 * @Date: 2020/5/25 21:02
 */
@Configuration
public class LoginInterceptor implements WebMvcConfigurer {
    @Autowired
    private AllInterceptor allInterceptor;
    @Autowired
    private CustomerInterceptor customerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(allInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "你的登陆路径",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
        InterceptorRegistration registration2 = registry.addInterceptor(customerInterceptor);
        registration2.addPathPatterns("/**");
        registration2.excludePathPatterns(
                "/page/account_login",            //登录
                "/login/**",
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf",
                "/everyone/testJson"
        );

    }
}
