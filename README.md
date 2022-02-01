# SpringBoot

## Hello World

1. 通过 IDEA Maven 创建工程

2. 导入依赖

   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.3.4.RELEASE</version>
   </parent>
   
   
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
   </dependencies>
   ```

3. 在 `main/resource` 下创建 **application.properties** 作为项目统一的配置文件

   ```properties
   # 修改 web 应用启动端口号
   server.port=8888
   ```

   更多配置参考：https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties

4. 在 `main/java` 下创建包和对应的 **MainApplication** 作为程序的启动类

   ```java
   @SpringBootApplication // 标注该类为主程序启动类
   public class MainApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(MainApplication.class, args);
       }
   
   }
   ```

5. 在**主程序包下**创建控制器和对应的请求接口方法

   ```java
   @RestController
   public class TestController {
   
       @RequestMapping("/hello")
       public String testHello() {
           return "hello";
       }
   
   
   }
   ```

6. 通过 **MainApplication** 左侧的小箭头启动应用

   ![image-20220126133613089](README.assets/image-20220126133613089.png)

7. 访问` localhost:8888/hello` 测试

8. [打包项目] 引入依赖

   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>
       </plugins>
   </build>
   ```

9. 通过 IDEA 右侧工具快速打包

   ![image-20220126134106248](README.assets/image-20220126134106248.png)

10. 关闭 IDEA 启动的服务，通过命令行 `java -jar jar包名` 也可以启动项目

    ![image-20220126134221048](README.assets/image-20220126134221048.png)

11. 访问接口测试

## 注解使用

### @Configuration

作用：标注该类是一个配置类(相当于配置文件)

使用：配合 **@Bean** 向容器中添加组件

```java
@Configuration // 标注该类为一个配置类
public class CustomConfig {

    /**
     * 通过 @Bean 注解向容器中注册组件，组件的 key(id) 为方法名，返回值就是组件实例
     * @return
     */
    @Bean
    public User user01() {
        return new User("zhangsan", 18);
    }

    /**
     * 也可以通过 @Bean.value 属性指定组件的 key(id)
     * @return
     */
    @Bean("dog")
    public Pet pet01() {
        return new Pet("dog");
    }

}
```

注意：

1. 配置类本身也会作为组件注册到 IOC 容器中

2. 配置类中通过 **@Bean** 配置的组件，默认也是**单实例**的

3. 其属性  `proxyBeanMethods` 可以用来调整 **Full / Lite** 模式

   当其为 `false` 时，表示 **Lite** 模式，代表不会为当前配置类生成代理对象，调用该代理类中的有关 **@Bean** 注解的方法时，会直接调用而不是现在容器中查找

   当其为 `true` 时，表示 **Full** 模式，代表会为当前配置类生成代理对象，调用该代理类中的有关 **@Bean** 注解的方法时，会先在容器中找对应的组件

   ```java
   @Bean
   public User user01() {
       // Lite: 会在调用依次 pet01() 方法; Full: 会现在容器中找对应的方法返回的组件
       System.out.println(pet01());
       return new User("zhangsan", 18);
   }
   
   @Bean("dog")
   public Pet pet01() {
       return new Pet("dog");
   }
   ```

   **主要是解决组件依赖的问题**，建议当不需要使用上述情况(一个组件需要调用另一个注册的组件)时，可以手动设置为 `false` 加速容器启动过程

### @Import

作用：导入组件

使用：在类上使用该注解导入指定组件

```java
@Configuration // 标注该类为一个配置类
@Import({DBHelper.class, User.class})
public class CustomConfig {
```

该注解会调用指定**类的无参构造器**创建组件实例后以**类的全类名**作为 key 保存到容器中

### @Conditional

作用：在注册组件时，需要满足相对应的条件才可以注册该组件

![image-20220127141215670](README.assets/image-20220127141215670.png)

使用：在注册组件时使用即可

```java
@ConditionalOnBean(name = "tom") // 只有当容器中存在 key 为 tom 的组件时才会注册该组件(CustomConfig)
public class CustomConfig {
}

@ConditionalOnMissingBean({ Pet.class }) // 只有当容器中不存在 Pet 类型的组件时才会注册该组件
public User user01() {
}
```

### 配置绑定

> 读取 properties 文件中的内容并封装到 JavaBean 中，以供随时使用

在配置文件中定义要使用的属性

```properties
mycat.name=BYQ
mycat.price=100000
```

#### @EnableConfigurationProperties + @ConfigurationProperties

在组件中使用 **@ConfigurationProperties**

```java
@ConfigurationProperties(prefix = "mycat") // 通过 prefix/value 指定配置文件中要获取属性的前缀
public class Cat {
```

在配置类中使用 **@EnableConfigurationProperties**

```java
@EnableConfigurationProperties({Cat.class})
public class CustomConfig {
```

该注解有两个作用：

1. 为指定的类开启配置绑定功能
2. 将指定的类注册到 IOC 容器中

#### @Component + @ConfigurationProperties

```java
@ConfigurationProperties(prefix = "mycat") // 通过 prefix/value 指定配置文件中要获取属性的前缀
@Component
public class Cat {

    // 属性名需要和配置文件中的属性去掉前缀后的一样
    private String name;
    private Integer price;
 
    // 需要为读取的属性定义 set() 方法
    ...
}
```

## 开发小技巧

### Lombok

作用：简化 JavaBean 开发

使用：

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
   </dependency>
   ```

2. 为 IDEA 安装 Lombok 插件

   ![image-20220127144139596](README.assets/image-20220127144139596.png)

3. 在 JavaBean 上使用相关注解

   ```java
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
   ```

### Dev-tools

作用：自动重启

使用：

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-devtools</artifactId>
       <optional>true</optional>
   </dependency>a
   ```

2. 启动项目后进行任意修改后按下 **Ctrl + F9** 即可

> 用处不大

### Spring Initializr

作用：快速创建 SpringBoot 项目

使用：在 IDEA 中创建新项目/模块时可以使用

![image-20220127145822403](README.assets/image-20220127145822403.png)

![image-20220127145841488](README.assets/image-20220127145841488.png)

## 配置文件

### yaml 使用

> yaml 是一种非常适合做以数据为中的配置文件

#### 基本语法

- `key: value`, kv 之间存在空格
- 大小写敏感
- 使用缩进表示层级关系
- 缩进不允许使用 **tab**，只允许空格
- 缩进的空格数不重要，只要相同层级的元素左对齐即可
- `#` 表示注释
- 字符串无需加引号，`''`与`""`表示字符串内容会 被转义/不被转义(是否转义成普通字符串)

#### 数据类型

- 字面量：单个的，不可再分的值。例如：date / boolean / string / number / null

  ```yaml
  k: v
  ```

- 对象：键值对的集合。例如：map / hash / set / object

  ```yaml
  # 行内写法
  k: {k1:v1,k2:v2,k3:v3}
  # 或者
  k:
    k1: v1
    k2: v2
    k3: v3
  ```

- 数组：一组按一定排序方式排列的值。例如: array / list / queue

  ```yaml
  # 行内写法
  k: [v1,v2,v3]
  # 或
  k:
    - v1
    - v2
    - v3
  ```

#### 示例

```java
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;
    private String[] interests;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salarys;
    private Map<String, List<Pet>> allPets;

}

@Data
public class Pet {

    private String name;
    private Double weight;

}
```

在 `src/main/resources	` 下创建 `application.yaml` / `application.yml` (二者都可)

```yaml
person:
  userName: byqtxdy
  boos: false
  # 默认的日期格式用 /
  birth: 2021/10/20
  age: 18
  pet:
    name: 阿猫
    weight: 199.9
  interests: [ 篮球,敲代码,旅游 ]
  animal:
    - 狗狗
    - 猫
  score:
    english:
      first: 30
      second: 40
      third: 50
    math: [ 131,140,150 ]
    # 对象的行内写法，属性和属性值之间可以不用空格
    chinese: { first:128,second:136 }
  salarys: [ 3999,4999,5999 ]
  allPets:
    sick:
      - { name: qiguai }
      - name: eihei
        weight: 1999
    # 如果对象数组需要使用行内写法，则对象的属性和属性值之间也要加空格
    health: [ { name: maomao,weight: 2999 },{ name: wangjile } ]
```

编写一个控制器方法，查看属性是否注入成功

![image-20220128100636721](README.assets/image-20220128100636721.png)

#### 配置提示

> 普通的 JavaBean 在编写时是没有提示

修改依赖文件

```xml
<!-- 添加依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>

<!-- 在打包的时候排除该依赖，应该该依赖只是在开发的使用有用，部署没必要 -->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

重启项目，再次编写配置文件

![image-20220128101733612](README.assets/image-20220128101733612.png)



## Web 

### 简单功能分析

#### 静态资源访问

默认的静态资源文件下路径：类路径(`/main/resources`)下的 `/static` or `/resources` or `/META-INF/resouces` or `/public`

原理：配置静态资源处理器映射关系为 `/**`，当游览器发送请求时，先判断是否有对应的请求接口，如果没有再有静态资源处理器处理

> 配置

1. 修改静态资源访问前缀

   ```yaml
   spring:
     mvc:
       static-path-pattern: /res/**
   ```

   作用：访问静态资源时需要加上前缀 `/res`

2. 修改静态资源文件夹路径

   ```yaml
   spring:
     resources:
       static-locations: classpath:/haha/
   ```

   让其他默认的四个静态资源文件夹全部失效，重新设置静态资源文件夹

> 扩展 - webjars

作用：将一些 JS/CSS 框架弄成了 jar 包，也可以在 Maven 中引入使用

使用：https://www.webjars.org/

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.webjars</groupId>
       <artifactId>jquery</artifactId>
       <version>3.5.1</version>
   </dependency>
   ```

2. 可以在 lib 模块下查看相关静态资源文件

   ![image-20220129075538562](README.assets/image-20220129075538562.png)

3. 以访问图上的 `jquery.js` 为例，访问 `/webjars/jquery/3.5.1/jquery.js` 即可

   ![image-20220129075846602](README.assets/image-20220129075846602.png)

   实际上就是 jar 包中静态资源的路径

#### 欢迎页支持

只需要在 静态资源文件夹 下创建 `index.html` 即可直接访问，但注意不能配置 `spring-mvc-static-path-pattern`(2.3.4 中是这样，好像2.5修复了，知道的小伙伴可以在评论区说一下)

![image-20220129080825688](README.assets/image-20220129080825688.png)

#### 自定义 Favicon

和配置 `index.html` 一样，默认名为 `favicon.ico` ，测试的时候注意重启游览器避免缓存即可

### 请求参数处理(注解)

- @RequestParam / @RequestHeader / @RequestBody / @CookieValue / @PathVariable

  基础使用可以参考 SpringMVC：TODO

  扩展：

  ```java
  /**
  * 扩展
  * {@code @PathVariable} 注解可以不写 value 后面定义一个 Map<String,String> 的形参，路径上的所有参数都会以 k v 的风格封装进去
  * {@code @RequestHeader} ~，本次请求的所有请求头信息都会以 k v 的风格封装技进去
  * {@code @RequestParam} ~, 本次请求的所有请求参数都会以 k v 的风格封装进去
  * {@code @CookieValue} 形参的类型可以设置为 Cookie 类型，会自动封装 Cookie 信息
  *
  * @param pathVars
  * @return
  */
  @GetMapping("/car/{id}/user/{username}")
  public Map getCar(@PathVariable Map<String, String> pathVars,
                    @RequestHeader Map<String, String> headers,
                    @RequestParam Map<String, String> params,
                    @CookieValue("_ga") Cookie _ga) {
      HashMap<String, Object> dataMap  = new HashMap<>();
      dataMap.put("pathVars", pathVars);
      dataMap.put("headers", headers);
      dataMap.put("params", params);
      dataMap.put("_ga", _ga);
      return dataMap;
  }
  ```

- @RequestAttribute

  作用：获取 request 域中的数据

  ```java
  @GetMapping("/go/success")
  public String goSuccess(Model model) {
      model.addAttribute("msg", "前往 success 请求...");
      model.addAttribute("code", 200);
      return "forward:/success";
  }
  
  @GetMapping("/success")
  @ResponseBody
  public Map success(@RequestAttribute("msg") String msg,
                     @RequestAttribute("code") Integer code) {
      HashMap<String, Object> dataMap = new HashMap<>();
      dataMap.put("msg", msg);
      dataMap.put("code", code);
      return dataMap;
  }
  ```

  注意：这个注解不能像前面几个一样用 Map 接收全部数据

- @MatrixVariable

  作用：获取矩阵变量的值

  > 矩阵变量：存在于路径变量之后，变量间用;隔开
  >
  > - 应用场景：当游览器禁用 Cookie 后，如果需要使用 session，就可以通过矩阵变量传递 **jsessionid** 标识客户端身份的同时与请求参数分开管理

  使用：

  1. 定制化 SpringBoot 解析请求路径的组件 **UrlPathHelper**

     ```java
     @Configuration
     public class WebConfig implements WebMvcConfigurer {
     
         /**
          * 定制化 SpringBoot 解析路径的 PathUrlHelper 组件
          * @param configurer
          */
         @Override
         public void configurePathMatch(PathMatchConfigurer configurer) {
             UrlPathHelper urlPathHelper = new UrlPathHelper();
             // 默认情况下，会将请求路径;后的所有东西后删除
             urlPathHelper.setRemoveSemicolonContent(false);
             configurer.setUrlPathHelper(urlPathHelper);
         }
     }
     ```

  2. 编写页面链接

     ```html
     <a href="/testMatrixVariable/testPath;name=zhangsan;age=16">(请求参数处理)测试 @testMatrixVariable</a>
     ```

  3. 编写接口

     ```java
     @GetMapping("/testMatrixVariable/{path1}")
     @ResponseBody
     public Map testMatrixVariable(@MatrixVariable("name") String name,
                                   @MatrixVariable("age") Integer age,
                                   @PathVariable("path1") String path1) {
         HashMap<String, Object> dataMap = new HashMap<>();
         dataMap.put("name", name);
         dataMap.put("age", age);
         dataMap.put("path1", path1);
         return dataMap;
     }
     ```

  4. 测试

  注意：

  1. 只有在 url 中使用的路径变量才可以使用矩阵变量

  2. 如果 url 中存在多个路径变量,可以通过属性 `pathVar` 解决

     ```java
     @GetMapping("/testMatrixVariable/{path1}/{path2}")
     @ResponseBody
     public Map testMatrixVariable(@MatrixVariable(value = "num", pathVar = "path1") String num1,
                                   @MatrixVariable(value = "num", pathVar = "path2") String num2){
     ```

     > /testMatrixVariable/test1;a=b/test2;c=d

### 视图解析和模板引擎

> SpringBoot 默认不支持 JSP，需要引入其他的第三方模板引擎技术实现页面渲染

#### 视图解析

视图处理方式

1. 转发
2. 重定向
3. 自定义视图

SpringBoot 支持的第三方模板引擎：

1. freemarker
2. groovy-templates
3. thymeleaf

#### thymeleaf

作用：和 JSP 差不多，且 SpringBoot 不支持 JSP

使用：学过 JSP 的话看一下表达式就好了，不难 https://www.yuque.com/atguigu/springboot/vgzmgh

![image-20220130190248888](README.assets/image-20220130190248888.png)


#### thymeleaf-hello world

1. 引入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
   ```

2. 在 `resources/templates`  下创建 html 文件

   ```html
   <!DOCTYPE html>
   <!-- 引入对应的命名空间 -->
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
       <head>
           <meta charset="UTF-8">
           <title>成功</title>
       </head>
       <body>
           <!-- th:text 会读取 request 域中的数据，如果不走模板渲染直接打开页面的话，会显示'默认字段' -->
           <p th:text="${msg}">默认字段</p>
       </body>
   </html>
   ```

3. 创建视图渲染接口

   ```java
   @GetMapping("/thymeleaf")
   public String toSuccess(Model model){
       model.addAttribute("msg", "Hello Thymeleaf");
       // 直接返回视图名即可, SpringBoot 底层会自动帮我们拼接
       return "test";
   }
   ```

4. 测试，访问接口

#### themeleaf-实战[后台管理]

前端后台模板：AdminEx

项目依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

> 这里只会记录本人学习时遇到的知识点，不如项目太细，每块都记的话太费时间

学习视频(感兴趣的可以跟着做一下)：https://www.bilibili.com/video/BV19K4y1L7MT?p=44

- 抽取模板页面公共部分

  1. 在 `templates` 下创建 common.html

     ![image-20220130211932014](README.assets/image-20220130211932014.png)

  2. 在其他模板页面中直接引用

     - 使用 `th:include`

       ```html
       <!-- 引用公共模板的语法都是一样 -> 公共页面相对于根目录的位置 :: 公共页面中的公共模板标识 -->
       <!-- th:include -> 用这里定义的外部元素(div)替换掉公共模板的的根元素 -->
       <div th:include="common :: common_link"></div>
       ```

       ![image-20220130212844188](README.assets/image-20220130212844188.png)

     - 使用 `th:replace`

       ```html
       <!-- th:replace -> 用公共模板直接替换掉当前定义的元素 -->
       <div th:replace="common :: common_script"></div>
       ```

       ![image-20220130212925704](README.assets/image-20220130212925704.png)

     

- thymeleaf 循环渲染数据

  ```html
  <tr class="gradeX" th:each="user,stats : ${userList}">
      <td th:text="${stats.count}"></td>
      <td th:text="${user.username}"></td>
      <td th:text="${user.password}"></td>
  </tr>
  ```

  第一个参数表示遍历的元素，第二个参数表示本次遍历进行的一个状态

### 拦截器 - 登录拦截

> 关于拦截器：TODO

1. 编写一个类，实现 **HandlerInterceptor** 接口并重写 `preHandle` 方法

   ```java
   public class LoginInterceptor implements HandlerInterceptor {
   
       /**
        * 在调用控制器接口之前被调用
        * @param request
        * @param response
        * @param handler
        * @return
        * @throws Exception
        */
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           Object userInfo = request.getSession().getAttribute("userInfo");
           if (userInfo != null){
               // 放行
               return true;
           }
           request.setAttribute("msg", "请先登录");
           // 转发到 / 请求
           request.getRequestDispatcher("/").forward(request, response);
           return false;
       }
   }
   ```

2. 在配置类中为该拦截器添加配置

   ```java
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
   
       /**
        * 添加拦截器
        * @param registry
        */
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
           registry.addInterceptor(new LoginInterceptor())
               .addPathPatterns("/**")
               .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/images/**", "/fonts/**");
       }
   }
   ```

3. 测试

### 文件上传

1. 编写前端页面

   ```html
   <form th:action="@{/upload}" method="post" enctype="multipart/form-data">
       邮箱：<input type="text" name="email" /> <br />
       名字: <input type="text" name="username" /> <br />
       头像: <input type="file" name="photo"> <br />
       照片集: <input type="file" name="imgs" multiple> <br />
   </form>
   ```

2. 编写接口

   ```java
   /**
   * 文件上传，如果存在不同的文件属性，可以通过 @RequestPart 指定参数名用来接收文件
   * @param photo 单文件
   * @param imgs 多文件
   */
   @PostMapping("/upload")
   public String uplaodFile(@RequestParam("email") String email,
                            @RequestParam("username") String username,
                            @RequestPart("photo") MultipartFile photo,
                            @RequestPart("imgs") MultipartFile[] imgs,
                            HttpSession session) throws IOException {
       log.info("request info: email={}, username={}, photo.size={}, imgs.length={}",
                email, username, photo.getSize(), imgs.length);
       // 获取保存图片文件夹路径
       String photoPath = session.getServletContext().getRealPath("photo");
       // 判断文件夹是否存在，如果不存在就创建对应的目录
       System.out.println(photoPath);
       File file = new File(photoPath);
       if (!file.exists()) {
           file.mkdir();
       }
       // 保存图片到本地服务器
       photo.transferTo(new File(photoPath + File.separator + photo.getOriginalFilename()));
       return "/home";
   }
   ```

3. 测试

### 异常处理

> SpringBoot 默认错误处理规则

- 根据错误的 HTTP 响应状态码，会在 `templates/error` 下找到对应的页面，例如 `code=404 -> templates/error/404.html` ;
-  如果不存在对应的页面，`code=404 -> templates/error/4xx.html`  会根据开头数字找 xx 页面; 
- 如果都不存在就会响应一个 `whitelabel` 视图

![image-20220131155007362](README.assets/image-20220131155007362.png)

> 定制错误处理逻辑

1. 自定义错误页

   在 `templates/error` 下创建对应的模板文件即可，

   可以通过 Postman 观察请求的响应信息，然后通过 thymeleaf 将错误信息渲染到视图上

   ![image-20220131155649354](README.assets/image-20220131155649354.png)

   ```html
   <h1 th:text="${status}"></h1>
   <h2 th:text="${error}"></h2>
   <h3 th:text="${message}"></h3>
   ```

   ![templates/error/4xx.html](README.assets/image-20220131155825801.png)

2. SpringMVC 中的 `@ControllerAdvice + @ExceptionHandler` 处理 web controller 全局异常：TODO

3. `@ResponseStatus`  + 自定义异常

   ```java
   /**
    * 自定义异常，通过 @ResponseStatus 注解标识如果出现该异常应该如何响应(code: 响应码, reason: 响应信息)
    * @author by Prover07
    * @classname CustomeException
    * @description TODO
    * @date 2022/1/31 16:00
    */
   @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "这是一个错误的请求")
   public class CustomException extends Exception{
   
       public CustomException() {
       }
   
       public CustomException(String message) {
           super(message);
       }
   }
   ```

   ```java
   @GetMapping("/testException")
   public String testHandleException() throws CustomException {
       throw new CustomException();
   }
   ```

   ![image-20220131160437402](README.assets/image-20220131160437402.png)

4. 自定义实现 HandlerExceptionResolver 处理异常；可以作为默认的全局异常处理规则

   ```java
   @Order(1) // 通过 @Order 注解可以提高该异常解析器的执行顺序，越小越早执行
   @Component
   public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
   
       /**
        * 解析异常，如果可以解析就返回 mv 对象，不可以就返回 null 即可
        * @param httpServletRequest
        * @param httpServletResponse
        * @param o
        * @param e
        * @return
        */
       @Override
       public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
           if (e instanceof CustomException) {
               return null;
           }
           return new ModelAndView();
       }
   
   }
   ```

### Web 原生组件注入(Servlet / Filter / Listener)

#### 原生组件注入

1. 使用 `@WebServlet` / `@WebFilter` / `@WebListener` 配置组件

   ```java
   /**
    * 通过 urlPatterns 属性配置 Servlet 映射 url
    * @author by Prover07
    * @classname MyServlet
    * @description TODO
    * @date 2022/1/31 17:31
    */
   @WebServlet(urlPatterns = "/my")
   public class MyServlet extends HttpServlet {
   
       @Override
       protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           resp.getWriter().write("巴御前天下第一");
       }
   }
   ```

   ```java
   /**
    * 通过 urlPattern 属性配置该过滤器会过滤的请求
    * @author by Prover07
    * @classname MyFilter
    * @description TODO
    * @date 2022/1/31 17:34
    */
   @Slf4j
   @WebFilter(urlPatterns = "/my")
   public class MyFilter implements Filter {
   
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           log.info("MyFilter inited...");
       }
   
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           log.info("MyFilter working...");
           filterChain.doFilter(servletRequest, servletResponse);
       }
   
       @Override
       public void destroy() {
           log.info("MyFilter destory...");
       }
   }
   ```

   ```java
   @Slf4j
   @WebListener
   public class MyListener implements ServletContextListener {
   
       @Override
       public void contextInitialized(ServletContextEvent sce) {
           log.info("ServletContext init...");
       }
   
       @Override
       public void contextDestroyed(ServletContextEvent sce) {
           log.info("ServletContext destory...");
       }
   }
   ```

2. 在启动类上添加 `@ServletComponentSacn` 注解

   ```java
   @ServletComponentScan("pers.prover07.boot.custom") // 标注原生组件所在位置
   @SpringBootApplication
   public class Boot05AdminApplication {
   ```

#### Spring 注入

1. 和第一种方式的第一步一样，但不用添加 `@WebServlet` / `@WebFilter` / `@WebListener` 注解

2. 创建配置类

   ```java
   @Configuration
   public class WebComponentConfig {
   
       @Bean
       public ServletRegistrationBean<MyServlet> myServlet() {
           return new ServletRegistrationBean<>(new MyServlet(), "/my");
       }
   
       @Bean
       public FilterRegistrationBean<MyFilter> myFilter() {
           FilterRegistrationBean<MyFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
           filterFilterRegistrationBean.setFilter(new MyFilter());
           filterFilterRegistrationBean.setUrlPatterns(Collections.singletonList("/my"));
           return filterFilterRegistrationBean;
       }
   
       @Bean
       public ServletListenerRegistrationBean<MyListener> myListener() {
           return new ServletListenerRegistrationBean<>(new MyListener());
       }
   
   }
   ```

### 定制化 SpringBoot 组件

1. 修改相关的配置文件
2. 编写配置类 @Configuration + @Bean 注解实现替换/增加容器中的默认组件
3. 编写配置类实现  **WebMvcConfigurer** 接口，通过重写接口内的方法实现定制化 web 功能
4. 使用 `@EnableWebMVC` +  **WebMvcConfigurer** 接口可以实现全面接管 SpringMVC(慎用，会导致部分功能失效)

## 单元测试(Junit5)

### 简介

> SpringBoot 2.2.0 版本开始引入 Junit5 作为单元测试默认库

新的 Junit 由三个不同子项目的不同模块组件：

- **JUnit Platform**: Junit Platform是在JVM上启动测试框架的基础，不仅支持Junit自制的测试引擎，其他测试引擎也都可以接入。

- **JUnit Jupiter**: JUnit Jupiter提供了JUnit5的新的编程模型，是JUnit5新特性的核心。内部 包含了一个**测试引擎**，用于在Junit Platform上运行。

- **JUnit Vintage**: 由于JUint已经发展多年，为了照顾老的项目，JUnit Vintage提供了兼容 JUnit4.x,Junit3.x的 测试引擎。

  ![img](README.assets/1606796395719-eb57ab48-ae44-45e5-8d2e-c4d507aff49a.png)

注意：在 SpringBoot 2.4 以上的版本默认移除了 Junit Vintage 的依赖，如果需要兼容 Junit4 需要自行引入

```xml
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

Junit5 依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 常用注解

- @Test：表示该方法是测试方法
- @DisplayName :为测试类或者测试方法设置展示名称
- @BeforeEach :表示在每个单元测试之前执行
- @AfterEach :表示在每个单元测试之后执行

- @BeforeAll :表示在所有单元测试之前执行
- @AfterAll :表示在所有单元测试之后执行
- @Disabled :表示测试类或测试方法不执行，类似于JUnit4中的@Ignore
- @Timeout :表示测试方法运行如果超过了指定时间将会返回错误
- @RepeatedTest: 可以指定要调用几次对应的测试方法

```java
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

    @RepeatedTest(5) // 重复调用5次
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
```

可以点击类旁白的绿色三角符号调用所有测试方法

![image-20220131193431222](README.assets/image-20220131193431222.png)

### 断言机制

作用：对测试中需要满足的条件进行验证，**这些断言方法都是 org.junit.jupiter.api.Assertions 的静态方法**。可以通过 Maven 插件提供的 test 功能调用所有测试方法并得到测试报告

注意：断言一旦失败，往下的代码都不会生效

#### 简单断言

用来对单个值进行简单的验证。如：

| 方法            | 说明                                 |
| --------------- | ------------------------------------ |
| assertEquals    | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals | 判断两个对象或两个原始类型是否不相等 |
| assertSame      | 判断两个对象引用是否指向同一个对象   |
| assertNotSame   | 判断两个对象引用是否指向不同的对象   |
| assertTrue      | 判断给定的布尔值是否为 true          |
| assertFalse     | 判断给定的布尔值是否为 false         |
| assertNull      | 判断给定的对象引用是否为 null        |
| assertNotNull   | 判断给定的对象引用是否不为 null      |

```java
@Test
void testAssertEquals() {
    // 第一个参数为预期的值，第二个参数为实际的值，第三个值为断言方法失败抛出的异常信息
    assertEquals(1, 2, "两值并不相等");
    // 其他的都差不多
}
```

#### 数组断言

通过 **assertArrayEquals** 方法来判断两个对象或原始类型的数组是否相等

```java
// 失败
//assertArrayEquals(new Object[]{new Object()}, new Object[]{new Object()});
// 成功
assertArrayEquals(new int[]{1,2,3}, new int[]{1,2,3});
```

#### 组合断言

**assertAll** 方法接受多个 org.junit.jupiter.api.Executable 函数式接口的实例作为要验证的断言，可以通过 lambda 表达式很容易的提供这些断言，只有该方法中的所有断言都通过该方法才可以通过

```java
@Test
void testAssertAll() {
    assertAll("测试失败",
              () -> assertNotSame(new Object(), new Object()),
              () -> assertTrue(1 == 1)
             );
}
```

#### 异常断言

**assertThrows** 配合上函数接口进行使用

```java
@Test
void testAssertThrows() {
    // 第一个参数为抛出的异常类型，第二个参数为执行的业务逻辑，第三个参数为提示信息
    assertThrows(NullPointerException.class, () -> {
        System.out.println("11111");
    },"未能抛出 NullPointerException 异常");
}
```

#### 超时断言

**assertTimeout()** 为测试方法设置了超时时间

```java
@Test
void testAssertTimeout() {
    // 第一个参数为 Duration 示例，第二个参数为要执行的业务逻辑，第三个参数为提示信息
    assertTimeout(Duration.ofMillis(500), () -> {
        Thread.sleep(1000);
    }, "超时啦");
}
```

#### 快速失败

通过 **fail** 方法使得测试直接失败

```java
@Test
void testFail() {
    fail("失败了");
}
```

#### 测试

1. 使用 IDEA 的 `test` 测试功能

   ![image-20220201113746627](README.assets/image-20220201113746627.png)
   

2. 查看控制台打印的测试报告

   ![image-20220201114041427](README.assets/image-20220201114041427.png)

### 前置条件

和断言类似，不同之处在于 **如果断言失败会使得测试方法失败**，而 **前置条件不成立只会使得测试方法的执行终止**，可以看做是测试方法执行的前提，当前提不满足时，没有继续执行的必要

```java
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
```

`assumeTrue() / assumeFalse()`: 只有第一个参数返回的结果为 True / False 时，剩下的代码才会执行，否则就会终止测试

`assumingThat()`: 只有第一个参数返回的结果为 True，才会执行第二个参数配置的业务逻辑，该方法无论第一个参数的返回结果为 True / False, 剩下的代码都会执行

### 嵌套测试

可以通过 Java 内部类 + `@Nested` 注解实现嵌套测试，从而更好的把相关的测试方法组织在一起

```java
@DisplayName("A stack")
class TestingAStackDemo {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}
```

注意：

1. 如果不在类上使用 `@Nested` 注解，那么该内部类中的测试方法 `@Test` 是无法生效的
2. 内部类执行时也会调用外部类编写的 `@BeforeEach` 相关注解的方法
3. 外部类的测试方法执行时并不会调用内部类配置 `@BeforeEach` 相关注解的方法

### 参数化测试

Junit5 中的新特性，它使得不同的参数多次运行测试成功了可能，利用以下注解可以实现随机传参：

- **@ValueSource**: 为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型
- **@NullSource**: 表示为参数化测试提供一个null的入参
- **@EnumSource**: 表示为参数化测试提供一个枚举入参
- **@CsvFileSource**：表示读取指定CSV文件内容作为参数化测试入参
- **@MethodSource**：表示读取指定方法的返回值作为参数化测试入参(注意方法返回**需要是一个流**)

```java
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
```







 

