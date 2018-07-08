package com.honsul.inthewood.core.jdbc;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CamelCaseMapTest {

  @Test
  public void testPut() throws Exception {
    CamelCaseMap<String, Object> map = new CamelCaseMap<>();
    map.put("userId", "ok");
    assertThat(map.get("userId"), is("ok"));
    map.clear();
    
    map.put("user_id",  "ok");
    assertThat(map.get("userId"), is("ok"));
    map.clear();
    
    map.put("USER_ID",  "ok");
    assertThat(map.get("userId"), is("ok"));
    map.clear();
    
    map.put("userid",  "ok");
    assertThat(map.get("userid"), is("ok"));
    map.clear();
    
    map.put("user_ID", "ok");
    assertThat(map.get("userId"), is("ok"));
    map.clear();
    
    map.put("User_ID", "ok");
    assertThat(map.get("userId"), is("ok"));
    map.clear();
    
    map.put("USERID", "ok");
    assertThat(map.get("userid"), is("ok"));
    map.clear();
  }

}
