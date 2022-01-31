package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import pers.prover07.boot.bean.User;

import javax.servlet.http.HttpSession;

/**
 * 认证控制器
 * @author by Prover07
 * @classname AuthController
 * @description TODO
 * @date 2022/1/30 20:27
 */
@Controller
public class AuthController {

    @PostMapping("/login")
    public String userLogin(User user, HttpSession session, Model model) {
        if (StringUtils.hasLength(user.getUsername()) && "123456".equals(user.getPassword())) {
            // 如果数据争取就在 session 中保存用户数据
            session.setAttribute("userInfo", user);
            return "redirect:/home";
        } else {
            model.addAttribute("msg", "账户密码错误");
            return "/login";
        }
    }

}
