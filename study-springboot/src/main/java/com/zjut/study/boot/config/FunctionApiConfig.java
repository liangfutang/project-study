package com.zjut.study.boot.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 函数式接口
 * @author tlf
 */
@Configuration
public class FunctionApiConfig {

    @Bean
    public RouterFunction<ServerResponse> functionApi() {
        return RouterFunctions.route()
                .GET("/v1/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON)
                        , request -> ServerResponse.ok()
                                .body("name = " + request.param("name").get() + ", id = " + request.pathVariable("id")))
                .POST("/v2/boot", RequestPredicates.accept(MediaType.APPLICATION_JSON)
                        , request -> ServerResponse.ok()
                                .body(request.body(JSONObject.class)))
                .build();
    }
}
