package pers.prover07.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assumptions.*;

/**
 * @author by Prover07
 * @classname Junit5Assumptions
 * @description TODO
 * @date 2022/2/1 11:53
 */
@DisplayName("测试前置条件")
public class Junit5Assumptions {

    @Test
    void testAssumeTrue() {
        // 第一个参数为前置条件(可以写对应的业务逻辑)，第二个参数为当条件未成立时抛出的信息
        assumeTrue(true, "前置条件没有成立");
        System.out.println("test assumeTrue()....");
        assumeFalse(() -> Objects.equals(new Object(), new Object()));
        System.out.println("test assumeFalse()...");
    }

    @Test
    void testAssumeThat() {
        // 第一个参数为前置条件，只有第一个参数为 true 才会执行的参数中第二个参数编写的业务逻辑
        assumingThat(1 == 1, () -> {
            System.out.println("test assumeThat...");
        });
        System.out.println("无论 assumingThat 第一个参数是否为 true，剩下的代码都会执行");
    }

}
