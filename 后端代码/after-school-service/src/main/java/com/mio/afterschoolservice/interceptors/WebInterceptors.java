package com.mio.afterschoolservice.interceptors;

import com.mio.afterschoolservice.utils.JwtUtil;
import com.mio.afterschoolservice.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebInterceptors implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token=request.getHeader("Authorization");
        //验证令牌
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String s = operations.get(token);
            if(s==null){
                throw new Exception();
            }
            Map<String,Object> claim= JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claim);
            return true;
        }
        catch (Exception e){
            response.setStatus(400);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
