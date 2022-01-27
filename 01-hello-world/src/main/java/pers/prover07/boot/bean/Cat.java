package pers.prover07.boot.bean;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by Prover07
 * @classname Cat
 * @description TODO
 * @date 2022/1/27 14:22
 */
@ConfigurationProperties(prefix = "mycat") // 通过 prefix/value 指定配置文件中要获取属性的前缀
@Data               // 自动为属性生成 get set 方法, 也可以使用  @Getter + @Setter
@NoArgsConstructor  // 自动生成无餐构造器
@AllArgsConstructor // 自动生成全餐构造器
@ToString           // 自动重写 toString()
@EqualsAndHashCode  // 自动重写 equals & hashCode
@Slf4j              // 为当前类自动注入一个 log 属性，用来进行日志打印
public class Cat {

    private String name;
    private Integer price;

}
