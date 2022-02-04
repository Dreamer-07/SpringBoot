package pers.prover07.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pers.prover07.boot.bean.Person;

/**
 * @author by Prover07
 * @classname BeanConfig
 * @description TODO
 * @date 2022/2/4 17:43
 */
@Configuration
public class BeanConfig {

    @Profile("prod")
    @Bean
    public Person person01() {
        return new Person("prod profile");
    }

    @Profile("test")
    @Bean
    public Person person02() {
        return new Person("test profile");
    }

}
