package com.touchsun.common.aspect;

import com.touchsun.common.annotation.NoAuth;
import com.touchsun.common.app.Result;
import com.touchsun.common.plugins.LoginPlugin;
import com.touchsun.common.plugins.base.Plugin;
import com.touchsun.common.plugins.type.PluginType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截切面
 *
 * @author lee
 */
@Aspect
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@Component
@Order(1000)
public class LoginAspect {

    @Value("${sso.active}")
    private Boolean active;
    
    @Value("${sso.server}")
    private String loginServer;

    @Value("${sso.api}")
    private String loginApi;
    
    @Around("execution(* com.touchsun.module.controller.*.*(..))")
    public Object handler(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes != null) {
            // 拿到原始Http请求处理
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            // 获取拦截点
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            // 获取登录注解
            NoAuth noAuth = method.getAnnotation(NoAuth.class);
            if (noAuth != null && noAuth.value()) {
                return point.proceed();
            }
            // 判断系统配置
            if (!active) {
                return point.proceed();
            }
            // 进行拦截
            Plugin plugin = new LoginPlugin(request, response, PluginType.LOGIN, loginServer, loginApi);
            if (!plugin.exec()) {
                log.error("未登录用户,禁止访问系统资源");
                return Result.unauthorized();
            }
        }
        return point.proceed();
    }
}
