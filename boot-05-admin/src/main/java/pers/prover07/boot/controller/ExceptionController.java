package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pers.prover07.boot.exception.CustomException;

/**
 * @author by Prover07
 * @classname ExceptionController
 * @description TODO
 * @date 2022/1/31 16:03
 */
@Controller
public class ExceptionController {

    @GetMapping("/testException")
    public String testHandleException() throws CustomException {
        throw new CustomException();
    }

}
