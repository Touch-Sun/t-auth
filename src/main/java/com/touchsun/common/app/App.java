package com.touchsun.common.app;

import cn.hutool.core.text.StrFormatter;
import lombok.*;

/**
 * 应用信息
 *
 * @author lee
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class App {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用代码
     */
    private String code;

    /**
     * 版本
     */
    private String version;

    @Override
    public String toString() {
        return StrFormatter.format(
                "应用名称: {}, 应用代码: {}, 应用版本: {}", name, code, version
        );
    }
}
