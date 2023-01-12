package com.touchsun;

import com.touchsun.common.annotation.NoAuth;
import com.touchsun.common.app.App;
import com.touchsun.common.app.Result;
import com.touchsun.common.app.Status;
import com.touchsun.common.constants.ConstantYml;
import com.touchsun.common.env.EnvironmentAware;
import com.touchsun.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth Template Main Application
 *
 * @author lee
 * @since 2023/1/4 20:11
 */
@SpringBootApplication
@RestController
@Slf4j
@MapperScan("com.touchsun.module.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/health")
    @NoAuth
    public Result<String> checkHealth() throws BaseException {
        log.info(Status.APP_HEALTH.getMessage());
        return Result.health(App.builder()
                .name(EnvironmentAware.getString(ConstantYml.APP_NAME))
                .code(EnvironmentAware.getString(ConstantYml.APP_CODE))
                .version(EnvironmentAware.getString(ConstantYml.APP_VERSION))
                .build().toString());
    }
}
