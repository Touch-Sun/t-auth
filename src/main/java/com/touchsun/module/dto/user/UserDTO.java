package com.touchsun.module.dto.user;

import com.touchsun.common.base.BaseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * oauth-template
 *
 * @author lee
 * @since 2023/1/4 23:08
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    /**
     * 登录账号
     */
    private String loginId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户名
     */
    private String userName;

    /**
     * token字符串
     */
    private String token;
}
