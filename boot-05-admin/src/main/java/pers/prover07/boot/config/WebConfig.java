package pers.prover07.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.prover07.boot.Interceptor.LoginInterceptor;

/**
 * @author by Prover07
 * @classname WebConfig
 * @description TODO
 * @date 2022/1/31 13:01
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/**/css/**", "/**/js/**", "/**/images/**", "/**/fonts/**");
    }
}
