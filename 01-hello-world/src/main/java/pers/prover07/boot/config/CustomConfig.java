package pers.prover07.boot.config;

import ch.qos.logback.core.db.DBHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pers.prover07.boot.bean.Cat;
import pers.prover07.boot.bean.Pet;
import pers.prover07.boot.bean.User;

/**
 * @author by Prover07
 * @classname CustomConfig
 * @description TODO
 * @date 2022/1/27 12:25
 */
@Configuration // 标注该类为一个配置类
@Import({DBHelper.class, User.class}) // 快速导入组件
@ConditionalOnBean(name = "tom") // 只有当容器中存在 key 为 tom 的组件时才会注册该组件(CustomConfig)
@EnableConfigurationProperties({Cat.class})
public class CustomConfig {

    /**
     * 通过 @Bean 注解向容器中注册组件，组件的 key(id) 为方法名，返回值就是组件实例
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({ Pet.class }) // 只有当容器中不存在 Pet 类型的组件时才会注册该组件
    public User user01() {
        System.out.println(pet01());
        return new User("zhangsan", 18);
    }

    /**
     * 也可以通过 @Bean.value 属性指定组件的 key(id)
     *
     * @return
     */
    @Bean("dog")
    public Pet pet01() {
        return new Pet("dog");
    }

}
