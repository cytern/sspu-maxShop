package com.funong.funong.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @Author: cytern
 * @Date: 2020/6/2 10:41
 */
@Aspect
@Component
public class SetType {
    @Before("execution(* com.funong.funong.controller.*.*(..))") // 所有controller包下面的所有方法的所有参数
    public void beforeMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
    }

}
