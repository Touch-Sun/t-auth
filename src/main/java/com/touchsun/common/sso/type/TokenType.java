package com.touchsun.common.sso.type;

import lombok.Getter;

/**
 * Token 生成模式
 *
 * @author lee
 */
public enum TokenType {

    /**
     * UUID模式
     */
    UUID(1, "UUID"),
    /**
     * JWT模式
     */
    JWT(2, "JWT");


    @Getter
    private final Integer code;

    @Getter
    private final String name;

    TokenType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 通过code值解析成类型
     *
     * @param model 模式值
     * @return 对应类型
     */
    public static TokenType parse(int model) {
        switch (model) {
            case 1:
                return UUID;
            case 2:
                return JWT;
            default:
        }
        return UUID;
    }
}
