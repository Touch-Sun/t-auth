package com.touchsun.common.sso.builder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.touchsun.common.constants.ConstantJwt;
import com.touchsun.common.constants.ConstantYml;
import com.touchsun.common.env.EnvironmentAware;
import com.touchsun.common.exception.BaseException;
import com.touchsun.common.sso.base.AbstractTokenBuilder;
import com.touchsun.module.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt Token生成器
 *
 * @author lee
 */
@Slf4j
@Component
public class JwtTokenBuilder extends AbstractTokenBuilder {

    public JwtTokenBuilder() {
        super();
    }

    public JwtTokenBuilder(User user) {
        super(user);
    }

    @Override
    public String generate() {
        try {
            // 过期时间
            Date expireDate = new Date(System.currentTimeMillis()
                    + EnvironmentAware.getLong(ConstantYml.JWT_EXPIRE) * 1000L);
            // 头部信息
            Map<String, Object> header = new HashMap<>(2);
            // 加密方式: HS256
            header.put(ConstantJwt.ALG, ConstantJwt.ENTRY_HS256);
            // 类型: JWT
            header.put(ConstantJwt.TYP, ConstantJwt.JWT);
            // 生成token
            return JWT.create().withHeader(header)
                    .withClaim(ConstantJwt.CLAIM_USER_ID, user.getId())
                    .withClaim(ConstantJwt.CLAIM_USERNAME, user.getUserName())
                    .withExpiresAt(expireDate)
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC256(EnvironmentAware.getString(ConstantYml.JWT_SECRET)));
        } catch (BaseException e) {
            e.printStackTrace();
            log.error("生成JwtToken失败,可能在获取环境变量时引发错误", e);
            return null;
        }
    }

    @Override
    public boolean remove() {
        return removeUser(user.getId());
    }

    @Override
    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(EnvironmentAware.getString(ConstantYml.JWT_SECRET));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJwt = verifier.verify(token);
            String userId = decodedJwt.getClaim(ConstantJwt.CLAIM_USER_ID).asString();
            if (userId != null) {
                return setUser(userId);
            } else {
                return false;
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                log.error("解析JwtToken失败,可能在获取环境变量时引发错误", e);
            } else if (e instanceof TokenExpiredException) {
                log.error("Token授权已过期", e);
            } else {
                log.error("解析JwtToken时引发错误", e);
            }
            return false;
        }
    }

    @Override
    public User parse() {
        return user;
    }
}
