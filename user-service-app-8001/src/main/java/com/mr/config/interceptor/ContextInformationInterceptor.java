package com.mr.config.interceptor;

import com.mr.common.ContextConstant;
import com.mr.common.ThreadLoalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaomanzhou
 * @date 2020/3/19 10:57 下午
 */

@Slf4j
@Component
public class ContextInformationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String namespace = request.getHeader("namespace");
        String token = request.getHeader("token");
        if(namespace != null && !namespace.trim().equals(""))
        {
            try
            {
                int namesapceInteger = Integer.parseInt(namespace);
                ThreadLoalCache.set(ContextConstant.NAMESPACE, namesapceInteger);
            } catch (NumberFormatException e)
            {
                log.info("传入非法namespace {}", e.getMessage());
            }
        }

        if(token != null && !token.trim().equals(""))
        {
            try
            {
                int tokenInteger = Integer.parseInt(token);
                ThreadLoalCache.set(ContextConstant.TOKEN, tokenInteger);
            } catch (NumberFormatException e)
            {
                log.info("传入非法token {}", e.getMessage());
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        ThreadLoalCache.remove(ContextConstant.NAMESPACE);
        ThreadLoalCache.remove(ContextConstant.TOKEN);
        super.afterCompletion(request, response, handler, ex);
    }
}
