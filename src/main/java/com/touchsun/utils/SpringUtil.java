package com.touchsun.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringBean 工具类
 *
 * @author lee
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.context = applicationContext;
    }

    public static <T> T getObject(String id) {
        return (T) context.getBean(id);
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
