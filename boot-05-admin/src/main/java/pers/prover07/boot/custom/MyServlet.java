package pers.prover07.boot.custom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过 urlPatterns 属性配置 Servlet 映射 url
 * @author by Prover07
 * @classname MyServlet
 * @description TODO
 * @date 2022/1/31 17:31
 */
//@WebServlet(urlPatterns = "/my")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("byqtxdy");
    }
}
