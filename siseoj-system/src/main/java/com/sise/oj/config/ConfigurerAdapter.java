package com.sise.oj.config;

import com.google.gson.GsonBuilder;
import com.sise.oj.base.SysConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring MVC自定义配置
 *
 * @author CiJee
 * @version 1.0
 */
@Configuration
public class ConfigurerAdapter implements WebMvcConfigurer {

    private final FileProperties properties;

    public ConfigurerAdapter(FileProperties properties) {
        this.properties = properties;
    }

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 配置静态访问资源
     *
     * @param registry 静态资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        FileProperties.ResPath path = properties.getPath();
        String avatarPath = "file:" + path.getAvatar().replace("\\", "/");
        String filePath = "file:" + path.getFile().replace("\\", "/");
        registry.addResourceHandler("/avatar/**").addResourceLocations(avatarPath);
        registry.addResourceHandler("/file/**").addResourceLocations(filePath);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Gson消息转换器
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(new GsonBuilder().setDateFormat(SysConstants.TIME_PATTERN).create());
        converters.add(converter);
        /*//1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2、添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue);
        //4、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //5、将convert添加到converters中
        converters.add(fastConverter);*/
    }

}
