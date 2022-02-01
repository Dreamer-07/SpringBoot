package pers.prover07.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author by Prover07
 * @classname Junit5AssertTest
 * @description TODO
 * @date 2022/2/1 10:44
 */
@DisplayName("Junit5 断言测试")
public class Junit5AssertTest {

    @Test
    void testAssertEquals() {
        // 第一个参数为预期的值，第二个参数为实际的值，第三个值为断言方法失败抛出的异常信息
        assertEquals(1, 2, "两值并不相等");
    }

    @Test
    void testAssertArrayEquals() {
        // 失败
        //assertArrayEquals(new Object[]{new Object()}, new Object[]{new Object()});
        // 成功
        assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
    }

    @Test
    void testAssertAll() {
        assertAll("测试失败",
                () -> assertNotSame(new Object(), new Object()),
                () -> assertTrue(1 == 1)
        );
    }

    @Test
    void testAssertThrows() {
        // 第一个参数为抛出的异常类型，第二个参数为执行的业务逻辑，第三个参数为提示信息
        assertThrows(NullPointerException.class, () -> {
            System.out.println("11111");
        }, "未能抛出 NullPointerException 异常");
    }

    @Test
    void testAssertTimeout() {
        // 第一个参数为 Duration 示例，第二个参数为要执行的业务逻辑，第三个参数为提示信息
        assertTimeout(Duration.ofMillis(500), () -> {
            Thread.sleep(1000);
        }, "超时啦");
    }

    @Test
    void testFail() {
        fail("失败了");
    }
}
