package pers.prover07.boot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.prover07.boot.custom.MyFilter;
import pers.prover07.boot.custom.MyListener;
import pers.prover07.boot.custom.MyServlet;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author by Prover07
 * @classname WebComponentConfig
 * @description TODO
 * @date 2022/1/31 17:43
 */
@Configuration
public class WebComponentConfig {

    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        return new ServletRegistrationBean<>(new MyServlet(), "/my");
    }

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        FilterRegistrationBean<MyFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new MyFilter());
        filterFilterRegistrationBean.setUrlPatterns(Collections.singletonList("/my"));
        return filterFilterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<MyListener> myListener() {
        return new ServletListenerRegistrationBean<>(new MyListener());
    }

}
