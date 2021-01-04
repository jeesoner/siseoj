package com.sise.oj.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.sise.oj.security.bean.SecurityProperties;
import com.sise.oj.util.RedisUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private static final String AUTHORITIES_KEY = "user";
    private JwtBuilder jwtBuilder;
    private JwtParser jwtParser;

    public TokenProvider(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
    }

    /**
     * 初始化Bean后就执行这个方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        // 使用SHA-512加密
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }

    public String createToken(Authentication authentication) {
        return jwtBuilder
                // 设置JWT_ID 使用UUID确保生成的Token都不一致
                .setId(IdUtil.simpleUUID())
                // 设置claim属性值
                .claim(AUTHORITIES_KEY, authentication.getName())
                // 设置主题
                .setSubject(authentication.getName())
                .compact();
    }

    public Claims getClaims(String token) {
        return jwtParser.parseClaimsJwt(token).getBody();
    }

    /**
     * 根据 token 获取鉴权信息
     *
     * @param token -
     * @return -
     */
    Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        // 使用 Spring Security 的User构造用户信息
        // 用户名 密码 权限
        User principal = new User(claims.getSubject(), "****", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    /**
     * 检查需要更新的 token
     */
    public void checkRenew(String token) {
        // 判断是否要续费token，计算token的过期时间，redis中的token key = 前缀+token
        long time = redisUtils.getExpire(properties.getOnlineKey() + token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断过期时间与当前时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if (differ <= properties.getDetect()) {
            long renew = time + properties.getRenew();
            redisUtils.expire(properties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
        }
    }


    /**
     * 获取请求里的token
     */
    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }
}
