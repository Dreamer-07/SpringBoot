package pers.prover07.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("pers.prover07.boot.custom") // 标注原生组件所在位置
@SpringBootApplication
public class Boot05AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot05AdminApplication.class, args);
    }

}
