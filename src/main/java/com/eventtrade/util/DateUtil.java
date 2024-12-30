package com.eventtrade.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface DateUtil {
     ZoneId TIME_ZONE = ZoneId.of("Europe/Moscow");
     DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
