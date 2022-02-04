package pers.prover07.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import pers.prover07.boot.bean.Person;

@SpringBootApplication
@EnableAdminServer
public class Boot06AdminServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Boot06AdminServerApplication.class, args);
        Person person = context.getBean(Person.class);
        System.out.println(person.getName());
    }

}
