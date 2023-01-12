package com.touchsun.common.plugins.type;

import lombok.Getter;

/**
 * 插件类型
 *
 * @author lee
 */
public enum PluginType {

    /**
     * 登录插件
     */
    LOGIN("登录插件", "plugin-login");

    @Getter
    private final String name;

    @Getter
    private final String id;

    PluginType(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
