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
  public void testSubstringBefore() {
    String str = "커서 안의 (내용)만 뽑아라.";
    assertEquals("커서 안의 ", TextUtils.substringBefore(str, "("));
    assertEquals(str, TextUtils.substringBefore(str, "X"));
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
