package com.touchsun.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import com.alibaba.fastjson.JSON;
import com.touchsun.common.constants.ConstantRedis;
import com.touchsun.common.sso.base.TokenManger;
import com.touchsun.module.entity.User;
import com.touchsun.common.sso.factory.TokenBuilderFactory;
import com.touchsun.module.service.AuthService;
import com.touchsun.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lee
 */
@Service("authService")
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public String generateToken(User user) {
        TokenManger tokenManger = TokenBuilderFactory.newInstance(user);
        return tokenManger.generate();
    }

    @Override
    public User verifyToken(String token) {
        TokenManger tokenManger = TokenBuilderFactory.newInstance();
        if (tokenManger.verify(token)) {
            return tokenManger.parse();
        }
        return null;
    }

    @Override
    public boolean removeToken(User user) {
        TokenManger tokenManger = TokenBuilderFactory.newInstance(user);
        if (tokenManger.remove()) {
            log.info("用户: {}, ID: {} 在 {} 时登出", user.getUserName(), user.getId(), DateUtil.now());
            return true;
        }
        return false;
    }

    @Override
    public void setLoginUser(User user) {
        String key = StrFormatter.format(ConstantRedis.LOGIN_USER, user.getId());
        redisUtil.set(key, JSON.toJSONString(user));
    }
}
