package com.haraji.common.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLogging {
    LogLevel level() default LogLevel.DEBUG;
    boolean logArguments() default false;
    boolean logResult() default true;
}
