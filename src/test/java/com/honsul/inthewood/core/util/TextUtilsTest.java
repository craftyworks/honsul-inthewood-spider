package com.honsul.inthewood.core.util;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TextUtilsTest {

  @Test
  public void testStringInBrackets() {
    assertThat(TextUtils.stringInBrackets("괄호 안의 (내용)만 뽑아라."), is("내용"));
    assertThat(TextUtils.stringInBrackets("첫번째 괄호 안의 (내용)만 (뽑아라)."), is("내용"));
    assertThat(TextUtils.stringInBrackets("괄호가 없으면 공백 문자"), is(""));
  }
  
  @Test
  public void testStringInLastBrackets() {
    assertThat(TextUtils.stringInLastBrackets("괄호 안의 (내용)만 뽑아라."), is("내용"));
    assertThat(TextUtils.stringInLastBrackets("두번째 괄호 안의 (내용)만 (뽑아라)."), is("뽑아라"));
    assertThat(TextUtils.stringInLastBrackets("괄호가 없으면 공백 문자"), is(""));
  }
  
  @Test
  public void testRemoveBrackets() {
    assertThat(TextUtils.removeBrackets("괄호(brackets) 안의 문자열을 지워라."), is("괄호 안의 문자열을 지워라."));
    assertThat(TextUtils.removeBrackets("괄호(brackets) 안의 문자열을(brackets) 지워라."), is("괄호 안의 문자열을 지워라."));
    assertThat(TextUtils.removeBrackets("괄호가 없으면 원본과 동일"), is("괄호가 없으면 원본과 동일"));
  }
  
 
  @Test
  public void testFindMoney() {
    String str = "53,000원";
    assertThat(TextUtils.findMoney(str), is("53000"));
    
    str = "77,000(20%)62,000";
    assertThat(TextUtils.findMoney(str), is("77000"));
    
    str = "(30%)54,000(50%)39,000";
    assertThat(TextUtils.findMoney(str), is("30"));
  }

  @Test
  public void testParseLong() {
    assertThat(TextUtils.parseLong("12345"), is(12345L));
    assertThat(TextUtils.parseLong("12,345"), is(12345L));
    assertThat(TextUtils.parseLong("12,345   "), is(12345L));
    assertThat(TextUtils.parseLong("12,345XX   "), is(0L));
  }

  @Test
  public void testRemove() {
    assertThat(TextUtils.remove("12,345", ","), is("12345"));
  }

  @Test
  public void testContains() {
    assertThat(TextUtils.contains("A", new String[] {"A", "B", "C"}), is(true));
    assertThat(TextUtils.contains("X", new String[] {"A", "B", "C"}), is(false));
  }

}
