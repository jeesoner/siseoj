package com.sise.oj.handler;

import com.sise.oj.annotation.TryAgain;
import com.sise.oj.exception.BusinessException;
import com.sise.oj.exception.TryAgainException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 乐观锁切面
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@Aspect
@Order(0)
@Component
public class TryAgainAspectHandler {

    @Pointcut("@annotation(com.sise.oj.annotation.TryAgain)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object LockerAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取拦截的方法签名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取目标对象
        Object target = proceedingJoinPoint.getTarget();
        // 这样才能拿到实现类的方法
        Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        // 获取注解信息
        TryAgain annotation = method.getAnnotation(TryAgain.class);
        // 设置最大重试次数
        int maxTryTimes = annotation.tryTimes();
        // 重试次数
        int attemptNumber = 0;
        do {
            try {
                // 执行业务代码
                return proceedingJoinPoint.proceed();
            } catch (TryAgainException e) {
                if (attemptNumber >= maxTryTimes) {
                    // 如果尝试次数大于最大次数，抛出异常
                    throw new BusinessException("服务器内部错误-------------重试失败");
                } else {
                    log.info("乐观锁: 重试第" + (++attemptNumber) + "次");
                }
            }
        } while (attemptNumber <= maxTryTimes);
        return null;
    }
}
