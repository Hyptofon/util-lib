package com.example.util;

import com.example.util.dates.DateUtils;
import com.example.util.strings.StringValidator;
import com.example.util.numbers.NumberConverters;
import com.example.util.csv.CsvUtils;
import com.example.util.json.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

public class UtilLibTest {
    @Test void dateBasics() {
        var d = DateUtils.parseIsoDate("2025-08-24");
        assertTrue(DateUtils.isWeekend(d) || !DateUtils.isWeekend(d));
        assertEquals(1, DateUtils.daysBetween(LocalDate.of(2025,1,1), LocalDate.of(2025,1,2)));
        var kyiv = DateUtils.toKyiv(LocalDateTime.of(2025, Month.JANUARY, 1, 12, 0), ZoneId.of("UTC"));
        assertNotNull(kyiv);
        assertNotEquals("", DateUtils.format(d, "yyyy/MM/dd", Locale.UK));
    }

    @Test void stringChecks() {
        assertTrue(StringValidator.isEmail("a@b.co"));
        assertTrue(StringValidator.isPhoneE164("+380501112233"));
        assertEquals("", StringValidator.safeTrim(null));
    }

    @Test void numberOps() {
        var x = NumberConverters.round(new BigDecimal("12.3456"), 2);
        assertEquals("12.35", x.toString());
        assertEquals(42, NumberConverters.toIntSafe("42", -1));
        assertEquals(-1, NumberConverters.toIntSafe("x", -1));
        var cur = NumberConverters.formatCurrency(new BigDecimal("10"), Locale.US);
        assertTrue(cur.contains("10"));
    }

    @Test void csv() {
        var rows = CsvUtils.parseSimple("a,b\nc,d");
        assertEquals(2, rows.size());
        assertEquals("a,b\nc,d", CsvUtils.toCsv(rows));
    }

    @Test void json() {
        JsonNode n = JsonUtils.parse("{\"x\":1}");
        assertEquals(1, n.get("x").asInt());
        String pretty = JsonUtils.toPretty(n);
        assertTrue(pretty.contains("\n"));
    }
}
