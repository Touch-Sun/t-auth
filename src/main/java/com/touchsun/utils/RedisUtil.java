package com.touchsun.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis 工具
 *
 * @author lee
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加key,value到缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据key获取value
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key移除value
     *
     * @param key 键
     * @return 结果
     */
    public boolean remove(String key) {
        return key != null && Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
