package com.touchsun.utils;

import com.alibaba.fastjson.JSONObject;
import com.touchsun.common.app.Result;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用
 *
 * @author lee
 */
@Component
public class FeignUtil {

    private final RestTemplate restTemplate;

    public FeignUtil() {
        restTemplate = new RestTemplate();
    }

    public <T> Result<T> post(String url, @Nullable Object formData, Class<T> responseType) {
        JSONObject res = restTemplate.postForObject(url, formData, JSONObject.class);
        if (res != null) {
            Result<T> result = res.toJavaObject(Result.class);
            result.setData(res.getObject("data", responseType));
            return result;
        } else {
            return null;
        }
    }

}
