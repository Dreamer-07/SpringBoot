package pers.prover07.boot.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author by Prover07
 * @classname MyComHealthIndicator
 * @description TODO
 * @date 2022/2/2 13:06
 */
@Component
public class MyComHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        HashMap<String, Object> infoMap = new HashMap<>();
        if (1 == 1) {
            builder.up();
            infoMap.put("count", 1);
            infoMap.put("ms", 100);
        } else {
            builder.status(Status.DOWN);
            infoMap.put("key", "鸡汤老咯");
            infoMap.put("key2", "heixiuxiu");
        }
        builder.withDetails(infoMap);
    }
}
