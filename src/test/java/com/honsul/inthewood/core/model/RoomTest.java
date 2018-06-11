package com.honsul.inthewood.core.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class RoomTest {
  final static Room a = new Room();
  final static Room b = new Room();
  final static Room c = new Room();
  
  @BeforeClass
  public static void init() {
    a.setResortId("R001");
    a.setRoomNm("휴양관 101호");
    
    b.setResortId("R001");
    b.setRoomNm("휴양관 101호");
    
    c.setResortId("R002");
    c.setRoomNm("휴양관 102호");
  }
  
  @Test
  public void testEquals() {
    assertThat(a.equals(b), is(true));
    assertThat(b.equals(a), is(true));
    assertThat(a.equals(c), is(false));
  }
  
  @Test
  public void testHashCode() {
    assertThat(a.hashCode(), equalTo(b.hashCode()));
    assertThat(a.hashCode(), not(equalTo(c.hashCode())));
  }

}
