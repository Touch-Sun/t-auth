package com.touchsun.common.plugins.base;

import com.touchsun.common.plugins.type.PluginType;

/**
 * 插件
 *
 * @author lee
 */
public interface Plugin {

    /**
     * 获取插件类型
     *
     * @return 插件类型
     */
    PluginType getType();

    /**
     * 运行插件
     *
     * @return 执行结果
     */
    boolean exec();

    /**
     * 后续处理
     */
    void after();
}
