package pers.prover07.boot.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author by Prover07
 * @classname CustomEndpoint
 * @description TODO
 * @date 2022/2/2 13:25
 */
@Component
// 标识 endpoint name
@Endpoint(id = "custom")
public class CustomEndpoint {

    @ReadOperation
    public Map getEndpointInfo() {
        return Collections.singletonMap("info", "custom endpint start...");
    }

    @WriteOperation
    public void restartEndpoint() {
        System.out.println("tnnd");
    }

}
