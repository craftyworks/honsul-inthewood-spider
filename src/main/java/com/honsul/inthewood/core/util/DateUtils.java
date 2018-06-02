package com.honsul.inthewood.core.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DateUtils {
  public static LocalDate fromEpochSecond(long second) {
    return Instant.ofEpochSecond(second).atOffset(ZoneOffset.ofHours(9)).toLocalDate();
  }
  
  public static long getEpochSecond() {
    return LocalDate.now().atStartOfDay(ZoneOffset.ofHours(9)).toInstant().getEpochSecond();
  }
  
  public static long getEpochSecond(LocalDate date) {
    return date.atStartOfDay(ZoneOffset.ofHours(9)).toInstant().getEpochSecond();
  }

}
