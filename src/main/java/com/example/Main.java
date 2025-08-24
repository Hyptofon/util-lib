package com.example;

import com.example.util.dates.DateUtils;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Сьогодні вихідний? " + DateUtils.isWeekend(LocalDate.now()));
    }
}
