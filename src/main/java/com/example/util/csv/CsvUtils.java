package com.example.util.csv;

import java.util.*;
import java.util.stream.Collectors;

public final class CsvUtils {
    private CsvUtils() {}

    // Простий CSV без лапок і ком
    public static List<String[]> parseSimple(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();
        String[] lines = csv.split("\\R");
        List<String[]> out = new ArrayList<>(lines.length);
        for (String line : lines) out.add(line.split(","));
        return out;
    }

    public static String toCsv(List<String[]> rows) {
        return rows.stream()
                .map(cols -> String.join(",", cols))
                .collect(Collectors.joining("\n"));
    }
}
