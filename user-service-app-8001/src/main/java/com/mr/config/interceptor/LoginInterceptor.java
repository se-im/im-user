package com.mr.config.interceptor;

import com.mr.common.RedisPrefixConst;
import com.mr.constant.TokenHashConst;
import com.mr.entity.po.User;
import com.mr.util.Jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaomanzhou
 * @date 2020/3/19 10:39 下午
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${token.expiration}")
    private int expiration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String token = request.getHeader("token");
        if(!Jwtutil.isValidate(token))
        {
            log.info("token不合法");
            request.getRequestDispatcher("/user/unlogin").forward(request, response);
            return false;
        }
        User user = (User)redisTemplate.opsForHash().get(RedisPrefixConst.TOKEN_PREFIX + token, TokenHashConst.USER);
        if(user == null)
        {
            log.info("token已过期");
            request.getRequestDispatcher("/user/unlogin").forward(request, response);
            return false;
        }else
        {
            redisTemplate.expire(RedisPrefixConst.TOKEN_PREFIX+token, expiration, TimeUnit.MINUTES);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(redisTemplate);
    }
}
