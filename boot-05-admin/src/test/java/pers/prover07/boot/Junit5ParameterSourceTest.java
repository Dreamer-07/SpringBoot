package pers.prover07.boot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author by Prover07
 * @classname Junit5ParamterSourceTest
 * @description TODO
 * @date 2022/2/1 12:29
 */
@DisplayName("Junit5 参数化测试")
public class Junit5ParameterSourceTest {

    @ParameterizedTest
    // 指定入参来源
    @ValueSource(strings = {"one", "tow", "three"})
    @DisplayName("参数化测试1")
    void parameterizedTest1(String str) {
        System.out.println(str);
        assertTrue(StringUtils.isNotBlank(str));
    }

    @ParameterizedTest
    // 指定方法返回值入参
    @MethodSource("method")
    @DisplayName("方法来源参数")
    public void testWithExplicitLocalMethodSource(String name) {
        System.out.println(name);
        Assertions.assertNotNull(name);
    }

    static Stream<String> method() {
        return Stream.of("apple", "banana");
    }



}
