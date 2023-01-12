package com.touchsun.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具
 * - 基于 Apache httpclient
 *
 * @author lee
 */
@Slf4j
public class HttpUtil {

    /**
     * HTTP GET 请求
     *
     * @param url    请求URL
     * @param params 参数
     * @return 响应结果
     */
    public static String get(String url, Map<String, String> params) {
        // 请求结果
        String result = null;
        // 响应对象
        CloseableHttpResponse response = null;
        // 请求地址
        String targetUrl = url;
        // Apache httpclient 客户端
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            if (params != null && !params.isEmpty()) {
                // Apache httpclient 参数列表
                List<NameValuePair> apacheParams = new ArrayList<>(20);
                for (Map.Entry<String, String> param : params.entrySet()) {
                    // 添加参数
                    apacheParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 转化为地址栏参数
                String pathParams = EntityUtils.toString(new UrlEncodedFormEntity(apacheParams, "UTF-8"));
                targetUrl += "?" + pathParams;
            }
            // Apache httpclient GET 请求
            HttpGet httpGet = new HttpGet(targetUrl);
            log.info("GET请求: {}", targetUrl);
            response = client.execute(httpGet);
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 解析响应
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    log.info("GET响应: {}", result);
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("请求异常 url: {} e: {}", targetUrl, e.toString());
        } finally {
            try {
                client.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("关闭HttpClient异常 e: {}", e.toString());
            }
        }
        return result;
    }

    /**
     * HTTP GET 请求
     * - 无请求参数
     *
     * @param url 请求URL
     * @return 响应结果
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * HTTP POST 请求
     *
     * @param url      请求URL
     * @param headers  请求头
     * @param formData 表单数据
     * @return 响应结果
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> formData) {
        // 请求结果
        String result = null;
        // Apache POST 请求
        HttpPost httpPost = null;
        // Apache httpclient 客户端
        CloseableHttpClient client = null;
        try {
            httpPost = new HttpPost(url);
            // 设置请求头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.setHeader(header.getKey(), header.getValue());
                }
            }
            // 设置请求体
            if (formData != null && !formData.isEmpty()) {
                List<NameValuePair> apacheFormData = new ArrayList<>(20);
                for (Map.Entry<String, String> item : formData.entrySet()) {
                    apacheFormData.add(new BasicNameValuePair(item.getKey(), item.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(apacheFormData, "UTF-8"));
            }
            // 设置POST请求配置
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000).build();
            httpPost.setConfig(config);
            // 失败不重试
            client = HttpClients.custom().disableAutomaticRetries().build();
            // 请求
            HttpResponse response = client.execute(httpPost);
            log.info("POST请求: {}, 参数: {}", url, formData);
            // 解析响应
            HttpEntity entity = response.getEntity();
            if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(entity, "UTF-8");
                // 确保响应entity完全被消费
                EntityUtils.consume(entity);
                log.info("POST响应: {}", result);
                return result;
            }
        } catch (IOException e) {
            log.error("请求异常 url: {} 参数: {} e: {}", url, formData, e.toString());
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.error("关闭HttpClient异常 e: {}", e.toString());
                }
            }
        }
        return result;
    }

    /**
     * HTTP POST 请求
     * - 无请求头
     *
     * @param url      请求URL
     * @param formData 表单数据
     * @return 响应结果
     */
    public static String post(String url, Map<String, String> formData) {
        return post(url, null, formData);
    }

    /**
     * HTTP POST 请求
     * - 无请求体
     *
     * @param url    请求URL
     * @param noData 无请求体
     * @return 响应结果
     */
    public static String post(String url, Map<String, String> headers, boolean noData) {
        return post(url, headers, null);
    }

}
