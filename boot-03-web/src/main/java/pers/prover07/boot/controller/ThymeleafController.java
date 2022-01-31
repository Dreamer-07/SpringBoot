package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author by Prover07
 * @classname ThymeleafController
 * @description TODO
 * @date 2022/1/30 19:53
 */
@Controller
public class ThymeleafController {

    @GetMapping("/thymeleaf")
    public String toSuccess(Model model){
        model.addAttribute("msg", "Hello Thymeleaf");
        // 直接返回视图名即可, SpringBoot 底层会自动帮我们拼接
        return "test";
    }

}
