package com.example.niukecommunity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：本次注解主要为了加在只有登录后才可以操作的方法上，加上该注解后方便拦截器进行拦截
 */
@Target(ElementType.METHOD)//申明该注解作用的范围(这里注解在方法上)
@Retention(RetentionPolicy.RUNTIME) //该注解作用的时刻
public @interface LoginRequired {
}
