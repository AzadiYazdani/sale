package com.haraji.app.config.swagger;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

class SwaggerEnabledCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        String swaggerEnabledConfig = context.getEnvironment().getProperty("swagger.enabled");
        if (!StringUtils.hasLength(swaggerEnabledConfig)) {
            return false;
        }
        return Boolean.parseBoolean(swaggerEnabledConfig);
    }
}
