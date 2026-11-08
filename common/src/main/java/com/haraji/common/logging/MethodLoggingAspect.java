package com.haraji.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class MethodLoggingAspect {

    @Around("@within(com.haraji.common.logging.MethodLogging) || " +
            "@annotation(com.haraji.common.logging.MethodLogging)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        MethodLogging methodAnnotation =
                AnnotatedElementUtils.findMergedAnnotation(method, MethodLogging.class);

        MethodLogging classAnnotation =
                AnnotatedElementUtils.findMergedAnnotation(targetClass, MethodLogging.class);

        MethodLogging annotation =
                methodAnnotation != null ? methodAnnotation : classAnnotation;

        if (annotation == null) {
            return joinPoint.proceed();
        }

        Logger logger = LoggerFactory.getLogger(targetClass);
        String methodName = method.getName();
        String methodId = targetClass.getSimpleName() + "." + methodName;

        String receivedMessage = methodId + " received";

        if (annotation.logArguments()) {
            receivedMessage += ": arguments=" + Arrays.deepToString(joinPoint.getArgs());
        }

        writeLog(logger, annotation.level(), receivedMessage);

        try {
            Object result = joinPoint.proceed();

            if (annotation.logResult()) {
                writeLog(
                        logger,
                        annotation.level(),
                        methodId + " response: " + result
                );
            }

            return result;
        } catch (Throwable exception) {
            logger.error("{} failed with exception: {}",
                    methodId, exception.getMessage(), exception);
            throw exception;
        }
    }

    private void writeLog(Logger logger, LogLevel level, String message) {
        switch (level) {
            case TRACE -> logger.trace("{}", message);
            case DEBUG -> logger.debug("{}", message);
            case INFO -> logger.info("{}", message);
            case WARN -> logger.warn("{}", message);
            case ERROR -> logger.error("{}", message);
        }
    }
}
