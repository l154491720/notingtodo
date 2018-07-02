package com.notingtodo.hystrix;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 在 Zuul 中实现熔断功能需要实现 ZuulFallbackProvider 的接口。实现改接口有两个方法，一个是getRoute() 方法。
 * 用于指定熔断功能应用于哪些路由的服务；另一个方法 fallbackResponse() 为进入熔断功能时执行的逻辑代码，
 * 当 basic-user 服务出现故障时，进入熔断逻辑，向浏览器输入一句错误提示。
 *
 * Created by qilin.liu on 2018/7/2.
 */
@Component
public class BasicUserFallbackProvider implements ZuulFallbackProvider{
    @Override
    public String getRoute() {
        return "basic-user";
//        return "*";   //用来匹配所有服务
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("调用用户服务出错".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}