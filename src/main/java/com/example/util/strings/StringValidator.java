package com.example.util.strings;

import java.util.regex.Pattern;

public final class StringValidator {
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_E164 =
            Pattern.compile("^\\+[1-9]\\d{7,14}$");

    private StringValidator() {}

    public static boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isEmail(String s) {
        return notBlank(s) && EMAIL.matcher(s).matches();
    }

    public static boolean isPhoneE164(String s) {
        return notBlank(s) && PHONE_E164.matcher(s).matches();
    }

    public static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
