package com.zmz.response.error;

/**
 * @author zhaomanzhou
 * @date 2020/3/19 11:27 下午
 */
public class BizException extends Exception
{
    public BizException(String message) {
        super(message);
    }
}
