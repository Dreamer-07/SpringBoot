package pers.prover07.boot.actuator.contributor;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author by Prover07
 * @classname ExampleInfoContributor
 * @description TODO
 * @date 2022/2/2 13:14
 */
@Component
public class ExampleInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("example", Collections.singletonMap("key", "value"));
    }
}
