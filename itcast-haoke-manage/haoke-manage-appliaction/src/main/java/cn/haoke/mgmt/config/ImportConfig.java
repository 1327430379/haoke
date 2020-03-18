package cn.haoke.mgmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:config/*.xml")
public class ImportConfig {
}
