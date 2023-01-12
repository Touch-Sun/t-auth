package com.touchsun.module.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.touchsun.module.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lee
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
