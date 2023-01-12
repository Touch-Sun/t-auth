package com.touchsun.common.app;

import lombok.Getter;

/**
 * 处理状态
 *
 * @author lee
 */
public enum Status {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAILED(500, "服务异常"),
    /**
     * 登录认证失败
     */
    AUTHENTICATION_FAILED(403, "认证失败"),
    /**
     * 权限认证失败
     */
    PERMISSION_FAILED(405, "您未被授权使用: {}功能, 请联系管理员开通"),
    /**
     * 应用健康检查
     */
    APP_HEALTH(203, "应用程序运行状况良好...");

    @Getter
    private final int code;

    @Getter
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
