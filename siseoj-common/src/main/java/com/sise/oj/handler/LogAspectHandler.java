package com.sise.oj.handler;

import com.sise.oj.base.SysConstants;
import com.sise.oj.utils.DateUtil;
import com.sise.oj.utils.IPAddressUtil;
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
import java.util.Objects;


/**
 * 接口调用日志记录 Handler
 *
 * @author CiJee
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class LogAspectHandler {

    @Value("${oj.config.logHandler.enable}")
    private boolean enable;
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date serviceDate = new Date();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
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
            String sb = "\n<---------------------- 进入请求 ---------------------->\n" +
                        "[业务发生时间]: {}\n" +
                        "[请求的URL]: {}\n" +
                        "[源IP]: {}\n" +
                        "[请求的类]: {}\n" +
                        "[调用方法]: {}\n" +
                        "[方法参数]: {}\n" +
                        "\n<----------------------------------------------------->\n";
            log.info(sb,
                    DateUtil.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    request.getRequestURL().toString(),
                    IPAddressUtil.getClientIPAddress(request),
                    className,
                    method.getName(),
                    params.toString());
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
            String sb = "\n<---------------------- 结束请求 ---------------------->\n" +
                        "[业务发生时间]: {}\n" +
                        "[响应耗时]: {}\n" +
                        "[响应内容]: {}\n" +
                        "\n<----------------------------------------------------->\n";
            log.info(sb, DateUtil.formatDate(serviceDate, SysConstants.TIME_PATTERN),
                    endTime - startTime < 0 ? "" : (endTime - startTime) + " ms",
                    obj == null ? "" : obj.toString());
        }
        return obj;
    }
}
