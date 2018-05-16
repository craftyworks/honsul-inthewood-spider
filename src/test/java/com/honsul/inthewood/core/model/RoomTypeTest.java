package com.honsul.inthewood.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoomTypeTest {

  @Test
  public void testRoomType() {
    assertEquals("HUT", RoomType.HUT.getCode());
    assertEquals("숲속의집", RoomType.HUT.getName());
    assertEquals(RoomType.HUT.name(), RoomType.HUT.toString());
  }
  
  @Test
  public void testGetRoomType() {
    assertEquals(RoomType.HUT, RoomType.getRoomType("숲속의집"));
    
    assertEquals(RoomType.CONDO, RoomType.getRoomType("숲속의집1"));
  }

}
