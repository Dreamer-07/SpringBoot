package pers.prover07.boot;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author by Prover07
 * @classname Junit5Test
 * @description TODO
 * @date 2022/1/31 19:24
 */
@DisplayName("Junit5 单元测试")
@SpringBootTest // 如果需要 Spring 驱动环境就需要打上该注解，不用则不需要
public class Junit5Test {

    @DisplayName("测试 @DisplayName")
    @Test
    void testDisplayName() {
        System.out.println("test @DisplayName...");
    }

    @Disabled
    @DisplayName("测试方法2")
    @Test
    void test2() {
        System.out.println("测试方法2...");
    }

    @DisplayName("test @Timeout")
    @Timeout(value = 500, unit = TimeUnit.MICROSECONDS)
    @Test
    void testTimeout() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RepeatedTest(5)
    void test4() {
        System.out.println("test @RepeatedTest...");
    }

    @BeforeEach
    void testBeforeEach() {
        System.out.println("test @BeforeEach");
    }

    @AfterEach
    void testAfterEach() {
        System.out.println("test @AfterEach");
    }

    @BeforeAll
    static void testBeforeAll() {
        System.out.println("test @BeforeAll");
    }

    @AfterAll
    static void testAfterAll() {
        System.out.println("test @AfterAll");
    }

}
