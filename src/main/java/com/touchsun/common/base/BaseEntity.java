package com.touchsun.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.touchsun.common.constants.ConstantNumber;
import com.touchsun.module.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础模型
 *
 * @author lee
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -4395340341177243472L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 逻辑删除
     */
    private Integer del;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 创建者名称
     */
    private String createUserName;

    /**
     * 更新者名称
     */
    private String updateUserName;

    public void parseUser(User user) {
        createUserId = user.getId();
        createUserName = user.getUserName();
        updateUserId = user.getId();
        updateUserName = user.getUserName();
    }

    public static BaseEntity newInstance(User user, boolean update) {
        BaseEntity baseEntity = new BaseEntity();
        if (!update) {
            baseEntity.parseUser(user);
            baseEntity.setCreateTime(new Date());
            baseEntity.setUpdateTime(new Date());
            baseEntity.setDel(ConstantNumber.ZERO);
        } else {
            baseEntity.setUpdateUserName(user.getUpdateUserName());
            baseEntity.setUpdateUserId(user.getId());
            baseEntity.setUpdateTime(new Date());
        }
        return baseEntity;
    }
}
