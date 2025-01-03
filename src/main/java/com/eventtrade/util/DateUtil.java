package com.eventtrade.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface DateUtil {
     ZoneId TIME_ZONE = ZoneId.of("Europe/Moscow");
     DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
     DateTimeFormatter DATE_FORMATTER_C = DateTimeFormatter.ofPattern("yyyy.MM.dd");

}
