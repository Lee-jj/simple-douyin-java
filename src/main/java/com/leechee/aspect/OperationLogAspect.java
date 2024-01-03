package com.leechee.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leechee.annotation.OperationLog;
import com.leechee.constant.JwtClaimsConstant;
import com.leechee.properties.JwtProperties;
import com.leechee.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.leechee.annotation.OperationLog)")
    public void operLogPointCut() {}
    
    /**
     * 环绕通知，记录方法的运行时长
     * @throws Throwable
     */
    @Around(value = "operLogPointCut()")
    public Object saveOperationLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("使用AOP记录操作日志");

        // 从jwt令牌中获取操作人id
        String userId = null;
        String token = request.getParameter(jwtProperties.getTokenName());
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            userId = claims.get(JwtClaimsConstant.USER_ID).toString();
        } catch (Exception e) {}
        System.out.printf("userId: %s\t", userId);

        // 获取当前操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        System.out.printf("operateTime: %s\t", operateTime);

        // 操作类名
        String className = joinPoint.getTarget().getClass().getName();        
        System.out.printf("className: %s\t", className);

        // 操作方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("methodName: %s\t", methodName);

        // 获取annotation中的description值，即操作描述
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = signature.getMethod().getAnnotation(OperationLog.class);
        String description = operationLog.description();
        System.out.printf("description: %s\t", description);

        // 获取IP地址
        String ipAddr = request.getRemoteAddr();
        System.out.printf("ipAddr: %s\t", ipAddr);

        // 获取请求URL
        String url = request.getRequestURL().toString();
        System.out.printf("url: %s\n", url);


        long begin = System.currentTimeMillis();

        // 调用原始目标方法运行
        Object result = joinPoint.proceed();

        
        long end = System.currentTimeMillis();

        // 记录操作耗时
        Long costTime = end - begin;
        System.out.printf("costTime: %s\n", costTime);

        // TODO 记录日志


        return result;
    }
}
