package com.touchsun.utils;


import com.touchsun.common.constants.ConstantHttp;

import javax.servlet.http.HttpServletRequest;

/**
 * Token 工具
 *
 * @author lee
 */
public class TokenUtil {


    /**
     * 获得Token
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader(ConstantHttp.TOKEN);
    }
}
