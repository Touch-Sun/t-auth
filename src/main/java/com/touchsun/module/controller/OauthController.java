package com.touchsun.module.controller;

import com.touchsun.common.annotation.NoAuth;
import com.touchsun.common.app.Result;
import com.touchsun.module.dto.user.UserDTO;
import com.touchsun.module.entity.User;
import com.touchsun.module.service.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 验证控制器
 *
 * @author lee
 */
@Api(tags = "验证接口")
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Resource
    private VerifyService verifyService;

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    @NoAuth
    public Result<UserDTO> password(@RequestBody UserDTO userDTO) {
        UserDTO auth = verifyService.password(userDTO);
        if (auth == null) {
            return Result.failed("用户名/密码错误");
        }
        return Result.success(auth);
    }

    /**
     * 登出
     */
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(verifyService.logout());
    }

    /**
     * token认证[内部服务鉴权使用]
     */
    @ApiOperation(value = "", hidden = true)
    @PostMapping("/verifyToken")
    @NoAuth
    public Result<User> verifyToken(@RequestBody UserDTO userDTO) {
        User user = verifyService.token(userDTO);
        if (user != null) {
            return Result.success(user, "认证成功");
        } else {
            return Result.failed("认证失败");
        }
    }

}
