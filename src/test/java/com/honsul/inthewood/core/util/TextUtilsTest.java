package com.honsul.inthewood.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextUtilsTest {

  @Test
  public void testStripCursor() {
    assertEquals("내용", TextUtils.stripCursor("커서 안의 (내용)만 뽑아라."));
    assertEquals("내용", TextUtils.stripCursor("첫번째 커서 안의 (내용)만 (뽑아라)."));
    assertEquals("", TextUtils.stripCursor("커서가 없으면 공백 문자"));
  }
  
  @Test
  public void testStripCursorLast() {
    assertEquals("내용", TextUtils.stripCursorLast("커서 안의 (내용)만 뽑아라."));
    assertEquals("뽑아라", TextUtils.stripCursorLast("두번째 커서 안의 (내용)만 (뽑아라)."));
    assertEquals("", TextUtils.stripCursorLast("커서가 없으면 공백 문자"));
  }
 
  @Test
  public void testFindMoney() {
    String str = "53,000원";
    assertEquals("53000", TextUtils.findMoney(str));
    str = "77,000(20%)62,000";
    assertEquals("77000", TextUtils.findMoney(str));
    str = "(30%)54,000(50%)39,000";
    assertEquals("30", TextUtils.findMoney(str));
  }

  @Test
  public void testParseLong() {
    assertEquals(12345L, TextUtils.parseLong("12345"));
    assertEquals(12345L, TextUtils.parseLong("12,345"));
    assertEquals(12345L, TextUtils.parseLong("12,345   "));
    assertEquals(0L, TextUtils.parseLong("12,345XX   "));
  }

  @Test
  public void testRemove() {
    assertEquals("12345", TextUtils.remove("12,345", ","));
  }

  @Test
  public void testContains() {
    assertEquals(true, TextUtils.contains("A", new String[] {"A", "B", "C"}));
    assertEquals(false, TextUtils.contains("X", new String[] {"A", "B", "C"}));
  }

}
