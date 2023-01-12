package com.touchsun.common.sso.base;

import cn.hutool.core.text.StrFormatter;
import com.alibaba.fastjson.JSON;
import com.touchsun.common.constants.ConstantRedis;
import com.touchsun.module.entity.User;
import com.touchsun.utils.RedisUtil;
import com.touchsun.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Token构造基类
 *
 * @author lee
 */
@Slf4j
public abstract class AbstractTokenBuilder implements TokenManger {

    /**
     * 用户信息
     */
    protected User user;

    public AbstractTokenBuilder() {

    }

    public AbstractTokenBuilder(User user) {
        this.user = user;
    }

    /**
     * 设置用户信息
     *
     * @param userId 用户ID,从Redis解析
     */
    public boolean setUser(String userId) {
        try {
            RedisUtil redisUtil = SpringUtil.getObject("redisUtil");
            String key = StrFormatter.format(ConstantRedis.LOGIN_USER, userId);
            String userInfo = redisUtil.get(key);
            if (userInfo != null) {
                user = new User();
                user = JSON.parseObject(userInfo, User.class);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析用户失败,可能在与Redis交互时引发错误", e);
            return false;
        }
    }

    public boolean removeUser(String userId) {
        try {
            RedisUtil redisUtil = SpringUtil.getObject("redisUtil");
            String key = StrFormatter.format(ConstantRedis.LOGIN_USER, userId);
            return redisUtil.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析用户失败,可能在与Redis交互时引发错误", e);
            return false;
        }
    }
}
