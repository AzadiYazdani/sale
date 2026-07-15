package com.haraji.common.util;

import com.haraji.common.constant.EntityType;
import com.haraji.common.constant.Language;

public final class MessageArgumentConverter {

    private MessageArgumentConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Object[] convert(Object[] args, Language language) {

        if (args == null || args.length == 0) {
            return args;
        }

        Object[] converted = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof EntityType entityType) {
                converted[i] = entityType.getValue(language);
            } else {
                converted[i] = arg;
            }
        }
        return converted;
    }

}
