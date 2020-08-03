package com.zmz.common;


/**
 * @author zhaomanzhou
 * @date 2020/3/19 10:45 下午
 */
public class RequestContext {

    public static String getToken()
    {
        return (String) ThreadLoalCache.get(ContextConstant.TOKEN);
    }


}
