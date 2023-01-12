package com.touchsun.common.sso;

import com.touchsun.module.entity.User;

/**
 * 线程共享用户
 *
 * @author lee
 * @since 2023/1/4 22:39
 */
public class UserHolder {

    private static final ThreadLocal<User> LOCAL_USER = new ThreadLocal<>();

    public static void set(User user) {
        LOCAL_USER.set(user);
    }

    public static User get() {
        if (LOCAL_USER.get() != null) {
            return LOCAL_USER.get();
        } else {
            User user = new User();
            user.setId("-1");
            user.setUserName("NoLoginUser");
            return user;
        }
    }

    public static void clear() {
        LOCAL_USER.remove();
    }
}
