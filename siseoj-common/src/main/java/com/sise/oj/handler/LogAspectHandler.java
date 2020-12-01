package com.sise.oj.handler;

import com.sise.oj.base.SysConstants;
import com.sise.oj.utils.DateUtils;
import com.sise.oj.utils.IPAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;


/**
 * @author CiJee
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class LogAspectHandler {

    @Value("${oj.config.logHandler.enable}")
    private boolean enable;
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.*Mapping)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date serviceDate = new Date();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取调用类的信息
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        // 获取调用方法信息
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        //获取参数类型
        StringBuilder params = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            for (Parameter parameter : parameters) {
                params.append("[").append(parameter.getType().getName()).append("]").append(parameter.getName()).append(" ");
            }
        }
        // 返回对象
        Object obj;
        Object[] args = proceedingJoinPoint.getArgs();
        // 如果启动该功能
        if (enable) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n---------------------- 进入异常处理 ----------------------\n");
            sb.append("[业务发生时间]: {}\n");
            sb.append("[请求的URL]: {}\n");
            sb.append("[源IP]: {}\n");
            sb.append("[请求的类]: {}\n");
            sb.append("[调用方法]: {}\n");
            sb.append("[方法参数]: {}\n");
            sb.append("\n-------------------------------------------------------\n");
            log.error(sb.toString(),
                    DateUtils.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    request.getRequestURL().toString(),
                    IPAddressUtils.getClientIPAddress(request),
                    className,
                    method.getName(),
                    params.toString());
        }
        // 计算执行时间
        long startTime = System.currentTimeMillis();
        long endTime = 0;

        if (args.length > 0) {
            obj = proceedingJoinPoint.proceed(args);
        } else {
            obj = proceedingJoinPoint.proceed();
        }

        endTime = System.currentTimeMillis();
        if (enable) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n---------------------- 离开异常处理 ----------------------\n");
            sb.append("[异常发生时间]: {}\n");
            sb.append("[响应耗时]: {}\n");
            sb.append("[响应内容]: {}\n");
            sb.append("\n-------------------------------------------------------\n");
            log.error(sb.toString(), DateUtils.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    endTime - startTime > 0 ? "" : (endTime - startTime) + " ms",
                    obj == null ? "" : obj.toString());
        }
        return obj;
    }
}
