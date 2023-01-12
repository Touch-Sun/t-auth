package com.touchsun.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具
 *
 * @author lee
 */
@Slf4j
public class NetWorkUtil {

    /**
     * 获取请求IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String IP(HttpServletRequest request) {
        String unknown = "unKnown";
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isNotEmpty(ip) && !unknown.equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StrUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip) ? ip : request.getRemoteAddr();
        }
    }
}
