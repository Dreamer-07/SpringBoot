package pers.prover07.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by Prover07
 * @classname TestController
 * @description TODO
 * @date 2022/2/4 17:41
 */
@RestController
public class TestController {

    /**
     * 通过 @Value 注解绑定配置文件属性; 可以通过 : 设置默认值
     */
    @Value("${person.info:default str}")
    public String info;

    @GetMapping("/profiles")
    public String profiles() {
        return info;
    }

}
