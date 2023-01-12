package com.touchsun.common.env;

import com.touchsun.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 静态类中读取环境变量
 *
 * @author lee
 */
@Component
public class EnvironmentAware implements org.springframework.context.EnvironmentAware {

    private static Environment env;

    @Override
    @Autowired
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static String getString(String key) throws BaseException {
        String value = env.getProperty(key);
        if (value == null) {
            throw new BaseException("获取环境变量异常: " + key);
        }
        return value;
    }

    public static Integer getInteger(String key) throws BaseException {
        return Integer.parseInt(getString(key));
    }

    public static Long getLong(String key) throws BaseException {
        return Long.parseLong(getString(key));
    }

    public static Float getFloat(String key) throws BaseException {
        return Float.parseFloat(getString(key));
    }

    public static Boolean getBoolean(String key) throws BaseException {
        return Boolean.parseBoolean(getString(key));
    }

    public static Double getDouble(String key) throws BaseException {
        return Double.parseDouble(getString(key));
    }
}
