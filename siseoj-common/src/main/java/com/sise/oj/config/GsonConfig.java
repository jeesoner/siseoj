package com.sise.oj.config;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Gson消息转换配置类
 *
 * @author CiJee
 * @version 1.0
 */
@Configuration
public class GsonConfig {

    @Value("${spring.gson.date-format}")
    private String dateFormat;

    @Bean
    public HttpMessageConverters customConverters() {
        // 消息转换器
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // Gson消息转换器
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(new GsonBuilder().setDateFormat(dateFormat).create());
        messageConverters.add(converter);
        return new HttpMessageConverters(true, messageConverters);
    }
}
