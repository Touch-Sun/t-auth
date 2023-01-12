package com.touchsun.module.service;


import com.touchsun.module.entity.User;

/**
 * 授权相关
 *
 * @author lee
 */
public interface AuthService {

    /**
     * 生成Token字符串
     *
     * @param user 用户信息
     * @return token 字符串
     */
    String generateToken(User user);

    /**
     * 验证Token字符串
     *
     * @param token token
     * @return 用户信息
     */
    User verifyToken(String token);

    /**
     * 清除用户登录信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean removeToken(User user);

    /**
     * 向缓存中设置登录用户
     *
     * @param user 用户信息
     */
    void setLoginUser(User user);
}
