package com.peashoot.mybatis.mybatistest.component;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peashoot.mybatis.mybatistest.entity.CookieMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Resource
    private CookieMap cookieMap;

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String language = cookieMap.getValue(request, "lang");
                Locale locale = Locale.getDefault();
                if (!StringUtils.isEmpty(language)) {
                    cookieMap.put("lang", language);
                    String[] split = null;
                    try {
                        if (language.contains("_")) {
                            split = language.split("_");
                        } else if (language.contains("-")) {
                            split = language.split("-");
                        }
                        locale = new Locale(split[0], split[1]);
                    } catch (Exception ex) {
                        locale = Locale.getDefault();
                    }
                }
                return locale;
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {

            @Override
            /* 在请求处理之前进行调用（Controller方法调用之前） */
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {

                return true;
            }

            @Override
            /* 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后） */
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    @Nullable ModelAndView modelAndView) throws Exception {
            }

            @Override
            /* 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作） */
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                    @Nullable Exception ex) throws Exception {
            }
        }).addPathPatterns("/user");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}