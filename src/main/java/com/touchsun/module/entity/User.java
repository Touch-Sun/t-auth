package com.touchsun.module.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.touchsun.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 登录用户
 *
 * @author lee
 * @since 2023/1/4 22:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"password"})
@TableName(value = "user")
public class User extends BaseEntity {

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String userName;
}
