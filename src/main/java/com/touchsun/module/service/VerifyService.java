package com.touchsun.module.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.touchsun.module.dto.user.UserDTO;
import com.touchsun.module.entity.User;

/**
 * 用户认证相关
 *
 * @author lee
 */
public interface VerifyService extends IService<User> {

    /**
     * 登录认证
     *
     * @param userDTO 登录表单
     * @return 登录成功对象
     */
    UserDTO password(UserDTO userDTO);

    /**
     * Token认证
     *
     * @param userDTO token信息
     * @return 认证结果
     */
    User token(UserDTO userDTO);

    /**
     * 登出
     *
     * @return 结果
     */
    boolean logout();
}
