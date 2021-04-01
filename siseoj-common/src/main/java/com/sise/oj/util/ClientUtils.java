package com.sise.oj.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sise.oj.base.SysConstants;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 客户端工具类
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
public class ClientUtils {

    /**
     * 是否本地解析IP地址
     */
    private static boolean ipLocal = false;

    private static File file = null;

    /**
     * ip2region DB配置
     */
    private static DbConfig dbConfig;

    static {
        SpringContextHolder.addCallBacks(() -> {
            ipLocal = SpringContextHolder.getProperties("ip.local-parsing", false, Boolean.class);
            if (ipLocal) {
                // 该文件只给ip2region使用，可以不关闭，一直持有
                String path = "ip2region/ip2region.db";
                String name = "ip2region.db";
                try {
                    dbConfig = new DbConfig();
                    file = FileUtils.inputStreamToFile(new ClassPathResource(path).getInputStream(), name);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 解析客户端IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String getClientIPAddress(HttpServletRequest request) {
        // 获取HTTP代理字段
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // Apache http服务器 请求字段
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // Apache http服务器 请求字段
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            // 可能有多个IP以,分隔，一般取第一个IP
            return ip.split(",")[0];
        }
        if ("127.0.0.1".equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    /**
     * 获取浏览器信息
     *
     * @param request 请求
     * @return 浏览器信息
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getCityInfo(String ip) {
        if (ipLocal) {
            return getLocalCityInfo(ip);
        } else {
            return getHttpCityInfo(ip);
        }
    }

    /**
     * 根据ip获取详细地址（网络查询）
     */
    public static String getHttpCityInfo(String ip) {
        String api = String.format(SysConstants.Url.IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    /**
     * 根据ip获取详细地址（本地解析）
     */
    public static String getLocalCityInfo(String ip) {
        try {
            // 返回查询到IP数据
            DataBlock dataBlock = new DbSearcher(dbConfig, file.getPath())
                    .binarySearch(ip);
            // 获取区域
            String region = dataBlock.getRegion();
            // 获取详细地址
            String address = region.replace("0|", "");
            char symbol = '|';
            if (address.charAt(address.length() - 1) == symbol) {
                address = address.substring(0, address.length() - 1);
                return address.equals(SysConstants.REGION) ? "内网IP" : address;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

}
