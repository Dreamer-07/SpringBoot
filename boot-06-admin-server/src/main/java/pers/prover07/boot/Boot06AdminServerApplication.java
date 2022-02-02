package pers.prover07.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Boot06AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot06AdminServerApplication.class, args);
    }

}
