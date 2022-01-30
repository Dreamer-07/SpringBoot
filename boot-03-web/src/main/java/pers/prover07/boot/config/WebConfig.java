package pers.prover07.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author by Prover07
 * @classname WebConfig
 * @description TODO
 * @date 2022/1/30 17:29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 定制化 SpringBoot 解析路径的 PathUrlHelper 组件
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 默认情况下，会将请求路径;后的所有东西后删除
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
