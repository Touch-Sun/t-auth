package com.touchsun.common.app;

import cn.hutool.core.text.StrFormatter;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回
 *
 * @author lee
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 5193422085379903156L;

    /**
     * 数据
     */
    protected T data;

    /**
     * 状态码
     */
    protected int code;

    /**
     * 信息
     */
    protected String message;

    private Result() {
        super();
    }

    ;

    public static <T> Result<T> reply() {
        return new Result<>();
    }

    public static <T> Result<T> reply(int code) {
        Result<T> result = reply();
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> reply(int code, String message) {
        Result<T> result = reply();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> reply(int code, T data, String message) {
        Result<T> result = reply();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> reply(Status status) {
        Result<T> result = reply();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        return result;
    }

    public static <T> Result<T> reply(Status status, T data) {
        Result<T> result = reply();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success() {
        return reply(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data) {
        return reply(Status.SUCCESS.getCode(), data, Status.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data, String message) {
        return reply(Status.SUCCESS.getCode(), data, message);
    }

    public static <T> Result<T> failed() {
        return reply(Status.FAILED.getCode(), Status.FAILED.getMessage());
    }

    public static <T> Result<T> failed(String message) {
        return reply(Status.FAILED.getCode(), message);
    }

    public static <T> Result<T> unauthorized() {
        return reply(Status.AUTHENTICATION_FAILED.getCode(), Status.AUTHENTICATION_FAILED.getMessage());
    }

    public static <T> Result<T> unauthorized(T data) {
        return reply(Status.AUTHENTICATION_FAILED.getCode(), data, Status.AUTHENTICATION_FAILED.getMessage());
    }

    public static <T> Result<T> health() {
        return reply(Status.APP_HEALTH.getCode(), Status.APP_HEALTH.getMessage());
    }

    public static <T> Result<T> health(T data) {
        return reply(Status.APP_HEALTH.getCode(), data, Status.APP_HEALTH.getMessage());
    }

    public static <T> Result<T> unPermission(String model) {
        return reply(Status.PERMISSION_FAILED.getCode(), StrFormatter.format(Status.PERMISSION_FAILED.getMessage(), model));
    }

}
