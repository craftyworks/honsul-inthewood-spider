package com.honsul.inthewood.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextUtils {
  private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);
  
  private static Pattern P_CURSOR = Pattern.compile("\\(([^\\(\\)]*)\\)");
  
  private static Pattern P_MONEY = Pattern.compile("([0-9,]+)");
  /**
   * 괄호 내부의 문자열 추출.
   * <p>예: stripCursor("8인(51㎡)") 리턴 51㎡ 
   */
  public static String stringInBrackets(String str) {
    Matcher matcher = P_CURSOR.matcher(str);
    if(matcher.find()) {
      return matcher.group(1);
    }
    return "";
  }
  
  /**
   * 괄호 내부의 문자열 추출. 괄호가 여러개 일 때 뒤에서 첫번째 괄호에서 문자열을 추출한다.
   */
  public static String stringInLastBrackets(String str) {
    Matcher matcher = P_CURSOR.matcher(str);
    String rtn = "";
    while(matcher.find()) {
      rtn = matcher.group(1);
    }
    return rtn;
  }
  
  /**
   * 괄호로 감싸진 문자열을 삭제한다. 괄호를 포함하여 삭제.
   */
  public static String removeBrackets(String str) {
    return str.replaceAll("\\([^\\(\\)]*\\)", "");
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

  public static URL toURL(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      logger.error("failed", e);
    }
    return null;
  }


}
