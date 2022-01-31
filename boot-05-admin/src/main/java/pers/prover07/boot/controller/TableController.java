package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.prover07.boot.bean.User;

import java.util.Arrays;
import java.util.List;

/**
 * @author by Prover07
 * @classname TableController
 * @description TODO
 * @date 2022/1/30 21:04
 */
@Controller
@RequestMapping("/table")
public class TableController {

    @GetMapping("/basic_table")
    public String routeBaseTable() {
        return "table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String routeDynamicTable(Model model) {
        List<User> userList = Arrays.asList(
                new User("zhangsan", "123456"),
                new User("list", "234567"),
                new User("wangwu", "234566")
        );
        model.addAttribute("userList", userList);
        return "table/dynamic_table";
    }

    @GetMapping("/editable_table")
    public String routeEditTable() {
        return "table/editable_table";
    }

    @GetMapping("/responsive_table")
    public String routeResponseTable() {
        return "table/responsive_table";
    }

}
