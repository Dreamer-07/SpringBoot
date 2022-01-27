package pers.prover07.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover07.boot.bean.Cat;

/**
 * @author by Prover07
 * @classname TestController
 * @description TODO
 * @date 2022/1/26 13:35
 */
@RestController
public class TestController {

    @Autowired(required = false)
    private Cat cat;

    @RequestMapping("/hello2")
    public String testHello() {
        return "hello";
    }

    @RequestMapping("/cat")
    public Cat testCar() {
        return cat;
    }

}
