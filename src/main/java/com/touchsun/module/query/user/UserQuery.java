package com.touchsun.module.query.user;

import com.touchsun.common.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志查询
 *
 * @author lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {

    /**
     * 用户名称
     */
    private String userName;
}
