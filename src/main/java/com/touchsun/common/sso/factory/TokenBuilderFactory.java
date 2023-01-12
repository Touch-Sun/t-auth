package com.touchsun.common.sso.factory;


import com.touchsun.common.constants.ConstantYml;
import com.touchsun.common.env.EnvironmentAware;
import com.touchsun.common.exception.BaseException;
import com.touchsun.common.sso.base.AbstractTokenBuilder;
import com.touchsun.common.sso.builder.JwtTokenBuilder;
import com.touchsun.common.sso.builder.UuidTokenBuilder;
import com.touchsun.common.sso.type.TokenType;
import com.touchsun.module.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * Token生成工厂
 *
 * @author lee
 */
@Slf4j
public class TokenBuilderFactory {

    public static AbstractTokenBuilder newInstance(User user) {
        try {
            TokenType type = TokenType.parse(EnvironmentAware.getInteger(ConstantYml.TOKEN_MODEL));
            switch (type) {
                case UUID:
                    return new UuidTokenBuilder(user);
                case JWT:
                    return new JwtTokenBuilder(user);
                default:
            }
        } catch (BaseException e) {
            log.error("尝试获取环境变量: {} 发生错误,采用默认UUID的Token生成模式", ConstantYml.TOKEN_MODEL);
        }
        return new UuidTokenBuilder(user);
    }

    public static AbstractTokenBuilder newInstance() {
        try {
            TokenType type = TokenType.parse(EnvironmentAware.getInteger(ConstantYml.TOKEN_MODEL));
            switch (type) {
                case UUID:
                    return new UuidTokenBuilder();
                case JWT:
                    return new JwtTokenBuilder();
                default:
            }
        } catch (BaseException e) {
            log.error("尝试获取环境变量: {} 发生错误,采用默认UUID的Token生成模式", ConstantYml.TOKEN_MODEL);
        }
        return new UuidTokenBuilder();
    }
}
