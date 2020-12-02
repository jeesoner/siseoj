package com.sise.oj.config;

import com.sise.oj.base.SysConstants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author CiJee
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件大小限制
     */
    private Long maxSize;

    /**
     * 头像大小限制
     */
    private Long avatarMaxSize;

    private ResPath mac;

    private ResPath linux;

    private ResPath windows;

    public ResPath getPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(SysConstants.WIN)) {
            return windows;
        } else if (os.toLowerCase().startsWith(SysConstants.OS)) {
            return mac;
        }
        return linux;
    }

    @Getter
    @Setter
    public static class ResPath {

        /** 头像路径 */
        private String avatar;

        /** 文件路径 */
        private String file;
    }

}
