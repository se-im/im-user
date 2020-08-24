package com.im.user.annotation;


import springfox.documentation.annotations.ApiIgnore;


public @interface NeedLogin
{
    /**
     * 用来确定没有登录后跳到哪里
     * 如果有值，则使用returnUrl做为跳转，否则根据业务跳到指定url
     * @return
     */
    String returnUrl() default "unlogin";
}
