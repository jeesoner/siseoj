package com.sise.oj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}
