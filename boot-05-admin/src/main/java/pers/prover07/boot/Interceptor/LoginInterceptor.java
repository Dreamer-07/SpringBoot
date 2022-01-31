package pers.prover07.boot.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author by Prover07
 * @classname LoginInterceptor
 * @description TODO
 * @date 2022/1/31 12:57
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在调用控制器接口之前被调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo != null){
            // 放行
            return true;
        }
        request.setAttribute("msg", "请先登录");
        // 转发到 / 请求
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }
}
