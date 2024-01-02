package com.leechee.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationLogAspect {

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.leechee.annotation.OperationLog)")
    public void operLogPointCut() {}
    
}
