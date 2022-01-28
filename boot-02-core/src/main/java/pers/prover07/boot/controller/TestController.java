package pers.prover07.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.prover07.boot.bean.Person;

/**
 * @author by Prover07
 * @classname TestController
 * @description TODO
 * @date 2022/1/28 9:57
 */
@Controller
public class TestController {

    @Autowired
    private Person person;

    @ResponseBody
    @RequestMapping("/person")
    public Person testPerson(){
        return person;
    }

}
