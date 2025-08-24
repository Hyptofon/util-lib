package com.example.util.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberConverters {
    private NumberConverters() {}

    public static BigDecimal toDecimal(String s) {
        return new BigDecimal(s.trim());
    }

    public static BigDecimal round(BigDecimal x, int scale) {
        return x.setScale(scale, RoundingMode.HALF_UP);
    }

    public static String formatCurrency(BigDecimal x, Locale locale) {
        return NumberFormat.getCurrencyInstance(locale).format(x);
    }

    public static int toIntSafe(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return fallback; }
    }
}
