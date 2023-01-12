package com.touchsun.common.sso.builder;

import com.touchsun.common.sso.base.AbstractTokenBuilder;
import com.touchsun.module.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UUID Token构造器
 *
 * @author lee
 */
@Slf4j
@Component
public class UuidTokenBuilder extends AbstractTokenBuilder {

    public UuidTokenBuilder() {
        super();
    }

    public UuidTokenBuilder(User user) {
        super(user);
    }

    @Override
    public String generate() {
        return UUID.randomUUID() + "-" + user.getId();
    }

    @Override
    public boolean remove() {
        return removeUser(user.getId());
    }

    @Override
    public boolean verify(String token) {
        String[] tokens = token.split("-");
        String userId = tokens[tokens.length - 1];
        return setUser(userId);
    }

    @Override
    public User parse() {
        return user;
    }
}
