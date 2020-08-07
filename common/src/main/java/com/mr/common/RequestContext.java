package com.mr.common;


/**
 * @author zhaomanzhou
 * @date 2020/3/19 10:45 下午
 */
public class RequestContext {

    public static String getToken()
    {
        return (String) ThreadLoalCache.get(ContextConstant.TOKEN);
    }

    public static Integer getNamespace()
    {
        return (Integer) ThreadLoalCache.get(ContextConstant.NAMESPACE);
    }


}
