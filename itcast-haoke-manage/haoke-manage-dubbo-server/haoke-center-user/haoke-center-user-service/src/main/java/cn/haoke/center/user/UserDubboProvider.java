package cn.haoke.center.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.haoke.center.user.mapper")
@ComponentScan(basePackages = {
        "cn.haoke.center.user",
        "cn.haoke.common"
})
public class UserDubboProvider {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserDubboProvider.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
