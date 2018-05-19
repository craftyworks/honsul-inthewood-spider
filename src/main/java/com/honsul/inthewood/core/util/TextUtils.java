package com.honsul.inthewood.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class TextUtils {
  private static Pattern P_CURSOR = Pattern.compile("\\(([^\\(\\)]*)\\)");
  
  private static Pattern P_MONEY = Pattern.compile("([0-9,]+)");
  /**
   * 괄호 내부의 문자열을 리턴
   * <p>예: stripCursor("8인(51㎡)") 리턴 51㎡ 
   */
  public static String stripCursor(String str) {
    Matcher matcher = P_CURSOR.matcher(str);
    if(matcher.find()) {
      return matcher.group(1);
    }
    return "";
  }
  
  public static String findMoney(String str) {
    Matcher matcher = P_MONEY.matcher(str);
    if(matcher.find()) {
      return matcher.group(1).replaceAll(",", "");
    }
    return "0";
  }
  
  public static long findMoneyLong(String str) {
    return parseLong(findMoney(str));
  }

  /**
   * <p>Gets the substring before the first occurrence of a separator.
   * The separator is not returned.</p>
   */
  public static String substringBefore(final String str, final String separator) {
    return StringUtils.substringBefore(str, separator);
  }
  
  public static String substringAfter(final String str, final String separator) {
    return StringUtils.substringAfter(str, separator);
  }
  
  /**
   * 콤마와 스페이스가 포함된 숫자 표현 문자열을 long 으로 변환
   */
  public static long parseLong(String str) {
    String longValue = StringUtils.removePattern(str, "[,\\s*]");
    if(StringUtils.isNumeric(longValue)) {
      return Long.parseLong(longValue);
    }
    return 0;
  }
  
  /**
   * <p>Removes all occurrences of a substring from within the source string.</p>
   */
  public static String remove(final String str, final String remove) {
    return StringUtils.remove(str, remove);
  }
  
  /**
   * 문자열이 문자배열 요소에 포함되는지 검사.
   */
  public static boolean contains(String str, String[] strings) {
    return ArrayUtils.contains(strings, str);
  }
}
