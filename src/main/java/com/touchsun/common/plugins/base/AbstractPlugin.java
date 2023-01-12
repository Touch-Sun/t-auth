package com.touchsun.common.plugins.base;

import com.touchsun.common.plugins.type.PluginType;
import lombok.Data;

/**
 * 抽象插件
 *
 * @author lee
 */
@Data
public abstract class AbstractPlugin implements Plugin {

    /**
     * 插件类型
     */
    protected PluginType pluginType;

    public AbstractPlugin(PluginType pluginType) {
        this.pluginType = pluginType;
    }
}
