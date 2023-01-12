package com.touchsun.common.plugins;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.touchsun.common.app.Result;
import com.touchsun.common.plugins.type.PluginType;
import com.touchsun.common.sso.UserHolder;
import com.touchsun.module.dto.user.UserDTO;
import com.touchsun.module.entity.User;
import com.touchsun.common.app.Status;
import com.touchsun.common.constants.ConstantHttp;
import com.touchsun.common.plugins.base.AbstractPlugin;
import com.touchsun.utils.FeignUtil;
import com.touchsun.utils.TokenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录插件
 *
 * @author lee
 */
@Slf4j
public class LoginPlugin extends AbstractPlugin {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final FeignUtil feignUtil;

    @Getter
    @Setter
    private String loginServer;

    @Getter
    @Setter
    private String loginApi;

    public LoginPlugin(HttpServletRequest servletRequest,
                       HttpServletResponse servletResponse,
                       PluginType pluginType,
                       String server,
                       String api) {
        super(pluginType);
        request = servletRequest;
        response = servletResponse;
        loginServer = server;
        loginApi = api;
        feignUtil = new FeignUtil();
    }

    @Override
    public PluginType getType() {
        return getPluginType();
    }

    @Override
    public boolean exec() {
        String token = TokenUtil.getToken(request);
        return verify(token);
    }

    @Override
    public void after() {
        log.error("未登录用户,禁止访问资源");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "" + ConstantHttp.TOKEN + ",content-type");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            response.getWriter().println(JSONObject.toJSONString(Result.unauthorized(loginServer)));
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * token 验证
     */
    public boolean verify(String token) {
        if (StrUtil.isEmpty(token)) {
            return false;
        } else {
            UserDTO formData = UserDTO.builder().token(token).build();
            String url = getLoginApi() + ConstantHttp.VERIFY_TOKEN;
            try {
                Result<User> result = feignUtil.post(url, formData, User.class);
                if (result != null) {
                    if (Status.SUCCESS.getCode() == result.getCode()) {
                        try {
                            UserHolder.set(result.getData());
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("解析认证服务返回结果异常,返回结果为: {}", result);
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
