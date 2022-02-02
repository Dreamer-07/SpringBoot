package pers.prover07.boot.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * 添加 Health 端点信息
 * @author by Prover07
 * @classname MyHealthIndicator
 * @description TODO
 * @date 2022/2/2 12:53
 */
@Component
public class ByqTxdyHealthIndicator implements HealthIndicator {

    /**
     * 主要返回自定义的健康监控信息
     * @return
     */
    @Override
    public Health health() {
        // 模拟检查健康信息
        if (1 == 1) {
            // up 表示健康
            return Health.up()
                    .withDetail("status", "success")
                    .withDetail("code", 200)
                    .build();
        } else {
            // down 表示不健康
            return Health
                    //.down()
                    // 通过观察源码可以发现，底层是调用 status(Status.DOWN) 设置不同状态的，那这里也可以通过 status 设置(只要不是 Status.UP 都是不健康的)
                    .status(Status.UNKNOWN)
                    .withDetail("status", "fail")
                    .withDetail("code", 500)
                    .build();
        }
    }

}
