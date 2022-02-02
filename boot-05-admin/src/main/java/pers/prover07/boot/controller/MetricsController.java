package pers.prover07.boot.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by Prover07
 * @classname MetricsController
 * @description TODO
 * @date 2022/2/2 13:19
 */
@RestController
public class MetricsController {

    Counter counter;

    public MetricsController(MeterRegistry meterRegistry) {
        // 通过 MeterRegistry 构建的一个监控信息对象
        // 这里的 counter 是用来监控统计信息的
        counter = meterRegistry.counter("test.MeterRegistry.count");
    }

    @RequestMapping("/meterRegistry")
    public String testMeterRegistry() {
        counter.increment();
        return "test.meterRegistry";
    }

}
