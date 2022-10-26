package com.zjut.study.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author TLF
 * @name http请求工具类
 * @date 2018-04-12 13:59
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    static final String CHARSET = "UTF-8";

    private static HttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(60000, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(20);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    public static String getHttpGet(String url, Map<String, String> headParams,Map<String, Object> body, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) throws URISyntaxException, IOException {
        HttpGet httpGet;
        URIBuilder uriBuilder = new URIBuilder(url);
        if (body != null) {
            body.entrySet().forEach(key -> {
                uriBuilder.addParameter(String.valueOf(key), (String) body.get(key));
            });
        }
        url = URLDecoder.decode(uriBuilder.build().toString(), CHARSET);
        httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout));
        for (Map.Entry<String, String> entry : headParams.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }
        logger.info("request param:" + httpGet);
        return getResult(url, httpClient.execute(httpGet));
    }

    public static String getHttpPost(String url, Map<String, String> headParams, String bodyParams, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout));
        //设置参数
        if (headParams != null && !headParams.isEmpty()) {
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        ByteArrayEntity entity = new ByteArrayEntity(bodyParams.getBytes());
        httpPost.setEntity(entity);
        logger.info("request param:" + httpPost);
        return getResult(url, httpClient.execute(httpPost));
    }

    public static String getHttpPut(String url, Map<String, String> headParams, String bodyParams, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(getRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout));
        //设置参数
        if (!headParams.isEmpty()) {
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                httpPut.setHeader(entry.getKey(), entry.getValue());
            }
        }
        ByteArrayEntity entity = new ByteArrayEntity(bodyParams.getBytes());
        httpPut.setEntity(entity);
        logger.info("request param:" + httpPut);
        return getResult(url, httpClient.execute(httpPut));
    }

    public static String getHttpDelete(String url, Map<String, String> headParams, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(getRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout));
        //设置参数
        if (!headParams.isEmpty()) {
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                httpDelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        logger.info("request param:" + httpDelete);
        return getResult(url, httpClient.execute(httpDelete));
    }

//    public static String  multipartPost(String url, Map<String, String> headParams, Map<String, Object> multipartBody, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setConfig(getRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout));
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        //设置参数
//        if (!headParams.isEmpty()) {
//            for (Map.Entry<String, String> entry : headParams.entrySet()) {
//                httpPost.setHeader(entry.getKey(), entry.getValue());
//            }
//        }
//        // 设置其他参数
//        for (Map.Entry<String, Object> entry : multipartBody.entrySet()) {
//            // 上传的文件
//            if (entry.getValue() instanceof MultipartFile) {
//                MultipartFile file = (MultipartFile) entry.getValue();
//                try {
//                    builder.addBinaryBody(entry.getKey(), file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
//                } catch (IOException e) {
//                    logger.error("change file to stream error, e:" + e);
//                    return null;
//                }
//            } else {
//                builder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
//            }
//        }
//        HttpEntity httpEntity = builder.build();
//        httpPost.setEntity(httpEntity);
//        HttpResponse response = null;
//        try {
//            response = httpClient.execute(httpPost);
//        } catch (IOException e) {
//            logger.error("execute mutipartFrom error, e:" + e);
//            return null;
//        }
//        if (null == response || response.getStatusLine() == null) {
//            logger.info("Post Request For Url[{}] is not ok. Response is null", url);
//            return null;
//        } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//            logger.info("Post Request For Url[{}] is not ok. Response Status Code is {}", url,
//                    response.getStatusLine().getStatusCode());
//            return null;
//        }
//        try {
//            return EntityUtils.toString(response.getEntity());
//        } catch (IOException e) {
//            logger.error("change multipartFrom result to String error, e:" + e);
//            return null;
//        }
//    }


    /**
     * 设置http请求的相关超时时长，不传则默认为5秒
     * @param connectTimeout
     * @param connectionRequestTimeout
     * @param socketTimeout
     * @return
     */
    private static RequestConfig getRequestConfig(Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
        if (connectTimeout==null || connectTimeout<=0) {
            connectTimeout = 5000;
        }
        if (connectionRequestTimeout==null || connectionRequestTimeout<=0) {
            connectionRequestTimeout = 2000;
        }
        if (socketTimeout==null || socketTimeout<=0) {
            socketTimeout = 5000;
        }
        return RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
    }

    /**
    　* @description: 获取请求结果
      * @author TLF
    　* @date 2020/9/13 0:14
      * @param url 请求地址
     * @param response 请求返回
    　* @return {@link String}
    　* @throws
    　*/
    private static String getResult(String url, HttpResponse response) {
        logger.info("请求{}返回结果:{}", url, response);
        if (null == response) {
            return null;
        }
        try {
            String result = EntityUtils.toString(response.getEntity());
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                return result;
            } else {
                logger.error("请求{}返回错误码：{},{}", url, code,result);
            }
        } catch (Exception e) {
            logger.error("解析结果错误", e);
        } finally {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (Exception e){
                logger.error("将连接放回池子异常", e);
            }
        }
        return null;
    }
}
