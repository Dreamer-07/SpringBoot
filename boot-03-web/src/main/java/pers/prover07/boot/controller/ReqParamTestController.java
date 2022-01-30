package pers.prover07.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by Prover07
 * @classname ReqParamTestController
 * @description TODO
 * @date 2022/1/30 16:42
 */
@Controller
public class ReqParamTestController {


    /**
     * 扩展:
     * {@code @PathVariable} 注解可以不写 value 后面定义一个 Map<String,String> 的形参，路径上的所有参数都会以 k v 的风格封装进去
     * {@code @RequestHeader} ~，本次请求的所有请求头信息都会以 k v 的风格封装技进去
     * {@code @RequestParam} ~, 本次请求的所有请求参数都会以 k v 的风格封装进去
     * {@code @CookieValue} 形参的类型可以设置为 Cookie 类型，会自动封装
     *
     * @param pathVars
     * @return
     */
    @GetMapping("/car/{id}/user/{username}")
    @ResponseBody
    public Map getCar(@PathVariable Map<String, String> pathVars,
                      @RequestHeader Map<String, String> headers,
                      @RequestParam Map<String, String> params,
                      @CookieValue("__utmz") Cookie __utmz) {
        HashMap<String, Object> dataMap  = new HashMap<>();
        dataMap.put("pathVars", pathVars);
        dataMap.put("headers", headers);
        dataMap.put("params", params);
        dataMap.put("__utmz", __utmz);
        return dataMap;
    }

    @GetMapping("/go/success")
    public String goSuccess(Model model) {
        model.addAttribute("msg", "前往 success 请求...");
        model.addAttribute("code", 200);
        return "forward:/success";
    }

    @GetMapping("/success")
    @ResponseBody
    public Map success(@RequestAttribute("msg") String msg,
                       @RequestAttribute("code") Integer code) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("msg", msg);
        dataMap.put("code", code);
        return dataMap;
    }

    @GetMapping("/testMatrixVariable/{path1}")
    @ResponseBody
    public Map testMatrixVariable(@MatrixVariable("name") String name,
                                  @MatrixVariable("age") Integer age,
                                  @PathVariable("path1") String path1) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name);
        dataMap.put("age", age);
        dataMap.put("path1", path1);
        return dataMap;
    }

    @GetMapping("/testMatrixVariable/{path1}/{path2}")
    @ResponseBody
    public Map testMatrixVariable(@MatrixVariable(value = "num", pathVar = "path1") String num1,
                                  @MatrixVariable(value = "num", pathVar = "path2") String num2){
        return new HashMap(){{
            put("sum", num1 + num2);
        }};
    }
}
