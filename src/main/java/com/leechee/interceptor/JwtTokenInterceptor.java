package com.leechee.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.leechee.constant.JwtClaimsConstant;
import com.leechee.context.BaseContext;
import com.leechee.properties.JwtProperties;
import com.leechee.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor{
    
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取token
        String token = request.getParameter(jwtProperties.getTokenName());

        // 校验token
        try {
            log.info("jwt校验,{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id,{}", userId);
            // 保存当前登录用户的id，之后使用
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception e) {
            // 校验不通过返回401状态码
            // response.setStatus(401);
            // 返回json格式错误信息
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status_code\": 1, \"status_msg\": \"token无效\"}");
            return false;
        }
    }
}
