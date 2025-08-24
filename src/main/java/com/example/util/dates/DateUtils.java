package com.example.util.dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class DateUtils {
    private DateUtils() {}

    public static LocalDate parseIsoDate(String s) {
        return LocalDate.parse(s);
    }

    public static String format(LocalDate date, String pattern, Locale locale) {
        return date.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public static LocalDateTime toKyiv(LocalDateTime dt, ZoneId sourceZone) {
        ZonedDateTime z = dt.atZone(sourceZone);
        return z.withZoneSameInstant(ZoneId.of("Europe/Kyiv")).toLocalDateTime();
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }
}
