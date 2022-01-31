package pers.prover07.boot.custom;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 通过 urlPattern 属性配置该过滤器会过滤的请求
 * @author by Prover07
 * @classname MyFilter
 * @description TODO
 * @date 2022/1/31 17:34
 */
@Slf4j
//@WebFilter(urlPatterns = "/my")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter inited...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("MyFilter working...");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("MyFilter destory...");
    }
}
