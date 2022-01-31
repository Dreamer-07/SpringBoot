package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * 路由控制器
 *
 * @author by Prover07
 * @classname RouterController
 * @description TODO
 * @date 2022/1/30 20:24
 */
@Controller
public class RouterController {

    @GetMapping({"/", "/login"})
    public String routeLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String routeHome(HttpSession session, Model model) {
        return "home";
    }

    @GetMapping("/file")
    public String routeFileUpload(){
        return "form/upload";
    }

}
