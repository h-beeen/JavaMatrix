package kr.beeen.matrix.global.config;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorPropertyConfig {

    @Bean
    public ErrorProperties errorProperties() {
        return new ErrorProperties();
    }
}
