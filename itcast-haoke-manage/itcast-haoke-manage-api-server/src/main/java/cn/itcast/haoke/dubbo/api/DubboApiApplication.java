package cn.itcast.haoke.dubbo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboApiApplication {

    public static void main(String[] args) {
        //整合了Es和Redis后，引发了netty的冲突，需要在启动类中加入
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(DubboApiApplication.class, args);
    }
}