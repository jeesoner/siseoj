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
import java.util.Date;
import java.util.Objects;

/**
 * 接口调用日志记录 Handler
 *
 * @author Cijee [2020/11/26]
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class ExceptionLogAspectHandler {

    @Value("${oj.config.exceptionLogHandler.enable}")
    private boolean enable;

    // 拦截统一处理异常
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void pointCut() {}


    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date serviceDate = new Date();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取调用类的信息
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        // 获取调用方法信息
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        // 返回对象
        Object obj;
        Object[] args = proceedingJoinPoint.getArgs();
        // 如果启动该功能
        if (enable) {
            String sb = "\n<---------------------- 进入异常处理 ---------------------->\n" +
                        "[异常发生时间]: {}\n" +
                        "[请求的URL]: {}\n" +
                        "[源IP]: {}\n" +
                        "[请求的类]: {}\n" +
                        "[调用方法]: {}\n" +
                        "\n<------------------------------------------------------->\n";
            log.error(sb,
                    DateUtils.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    request.getRequestURL().toString(),
                    IPAddressUtils.getClientIPAddress(request),
                    className,
                    method.getName());
        }
        // 计算执行时间
        long startTime = System.currentTimeMillis();
        long endTime;

        if (args.length > 0) {
            obj = proceedingJoinPoint.proceed(args);
        } else {
            obj = proceedingJoinPoint.proceed();
        }

        endTime = System.currentTimeMillis();
        if (enable) {
            String sb = "\n<---------------------- 离开异常处理 ---------------------->\n" +
                        "[异常发生时间]: {}\n" +
                        "[响应耗时]: {}\n" +
                        "[响应内容]: {}\n" +
                        "\n<------------------------------------------------------->\n";
            log.error(sb, DateUtils.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    endTime - startTime > 0 ? "" : (endTime - startTime) + " ms",
                    obj == null ? "" : obj.toString());
        }
        return obj;
    }
}
