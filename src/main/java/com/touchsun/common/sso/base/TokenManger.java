package com.touchsun.common.sso.base;

import com.touchsun.module.entity.User;

/**
 * Token管理
 *
 * @author lee
 */
public interface TokenManger {

    /**
     * 生成Token字符串
     *
     * @return Token字符串
     */
    String generate();

    /**
     * 清除Token
     *
     * @return 结果
     */
    boolean remove();

    /**
     * 验证Token字符串
     *
     * @param token Token字符串
     * @return 验证结果
     */
    boolean verify(String token);

    /**
     * 解析用户信息
     *
     * @return 用户信息
     */
    User parse();
}
