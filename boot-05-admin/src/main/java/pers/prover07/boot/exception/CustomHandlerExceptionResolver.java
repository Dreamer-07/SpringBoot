package pers.prover07.boot.exception;

import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Prover07
 * @classname CustomHandlerExceptionResolver
 * @description TODO
 * @date 2022/1/31 16:05
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE) // 通过 @Order 注解可以提高该异常解析器的执行顺序，越小越早执行
@Component
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    /**
     * 解析异常，如果可以解析就返回 mv 对象，不可以就返回 null 即可
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof CustomException) {
            return null;
        }
        return new ModelAndView();
    }

}
