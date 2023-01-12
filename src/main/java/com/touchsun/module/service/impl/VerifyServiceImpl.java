package com.touchsun.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.touchsun.common.sso.UserHolder;
import com.touchsun.module.dto.user.UserDTO;
import com.touchsun.module.entity.User;
import com.touchsun.module.service.AuthService;
import com.touchsun.module.mapper.UserMapper;
import com.touchsun.module.service.VerifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lee
 */
@Service
public class VerifyServiceImpl extends ServiceImpl<UserMapper, User> implements VerifyService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthService authService;

    @Override
    public UserDTO password(UserDTO userDTO) {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getPassword, userDTO.getPassword())
                .and(w -> w
                        .eq(User::getPhone, userDTO.getLoginId())
                        .or()
                        .eq(User::getEmail, userDTO.getLoginId())
                );
        User user = userMapper.selectOne(userQuery);
        if (user != null) {
            // 生成访问令牌
            UserDTO loginRes = UserDTO.builder()
                    .token(authService.generateToken(user))
                    .build();
            // 设置当前登录用户
            authService.setLoginUser(user);
            // 返回
            return loginRes;
        } else {
            return null;
        }
    }

    @Override
    public User token(UserDTO userDTO) {
        User userEntity = authService.verifyToken(userDTO.getToken());
        if (userEntity != null) {
            User user = new User();
            userEntity = userMapper.selectById(userEntity.getId());
            BeanUtils.copyProperties(userEntity, user);
            return user;
        }
        return null;
    }

    @Override
    public boolean logout() {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        User user = UserHolder.get();
        userQuery.eq(User::getId, user.getId());
        return authService.removeToken(userMapper.selectOne(userQuery));
    }

}
