package com.ecommerce.backend;

import com.ecommerce.backend.dto.account.LoginAuthDto;
import com.ecommerce.backend.service.feign.FeignAccountAuthService;
import com.ecommerce.backend.service.feign.FeignConst;
import com.ecommerce.backend.service.impl.UserServiceImplement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@Slf4j
@EnableFeignClients
@EnableConfigurationProperties({LiquibaseProperties.class})
public class EcommerceBackendApplication {

    @Autowired
    FeignAccountAuthService accountAuthService;

    @Autowired
    UserServiceImplement userService;

    @Value("${auth.internal.username}")
    private String username;

    @Value("${auth.internal.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }

    @PostConstruct
    public void initialize() {
        MultiValueMap<String,String> request = new LinkedMultiValueMap<>();
        request.add("grant_type","password");
        request.add("username", username);
        request.add("password", password);
        LoginAuthDto result = accountAuthService.authLogin(FeignConst.LOGIN_TYPE_INTERNAL,request);
        if(result == null || result.getAccessToken() == null){
            throw new RuntimeException("APPLICATION FAILED TO START: CAN NOT GET KEY ");
        }
        userService.AUTH_SERVER_TOKEN = result.getAccessToken();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
