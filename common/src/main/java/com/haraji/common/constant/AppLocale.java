package com.haraji.common.constant;

import java.util.Locale;

public final class AppLocale {

    public static final Locale ENGLISH = Locale.US;
    public static final Locale PERSIAN = Locale.of("fa", "IR");

    private AppLocale() {
        throw new UnsupportedOperationException("Utility class");
    }

}
