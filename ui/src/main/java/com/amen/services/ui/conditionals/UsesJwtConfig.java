package com.amen.services.ui.conditionals;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class UsesJwtConfig implements Condition {
    @Override
    public boolean matches(ConditionContext c, AnnotatedTypeMetadata m) {
        return false;
    }
}

