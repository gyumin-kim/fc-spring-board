package example.springboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "example.springboard.dao",
                               "example.springboard.service"
})
@Import({ DBConfig.class })
// 비즈니스 객체들을 생성하는 Root Container(부모 컨테이너)
public class ApplicationConfig {

}