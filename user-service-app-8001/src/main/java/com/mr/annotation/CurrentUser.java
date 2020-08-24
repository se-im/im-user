package com.mr.annotation;


import org.springframework.core.annotation.AliasFor;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER}) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME)
@ApiIgnore
public @interface CurrentUser
{
}
