package com.honsul.inthewood.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.honsul.inthewood.core.model.RoomType;

public class NationalResortUtilsTest {

  @Test
  public void testLoginCookies() throws IOException {
    NationalResortUtils.loginCookies();
  }

  @Test
  public void testBookinDocument() throws IOException {
    Document doc = NationalResortUtils.bookinDocument("0103");
    assertThat(doc.toString().contains("월별예약현황"), is(true));
  }
  
  @Test
  public void testPrice() {
    assertThat(NationalResortUtils.price(RoomType.HUT, "4"), is(37000L));
    assertThat(NationalResortUtils.price(RoomType.HUT, "7"), is(58000L));
    assertThat(NationalResortUtils.price(RoomType.HUT, "9"), is(77000L));
    assertThat(NationalResortUtils.price(RoomType.CONDO, "4"), is(39000L));
    assertThat(NationalResortUtils.price(RoomType.CONDO, "8"), is(85000L));
    assertThat(NationalResortUtils.price(RoomType.CONDO, "11"), is(100000L));
    
    assertThat(NationalResortUtils.peakPrice(RoomType.HUT, "4"), is(67000L));
    assertThat(NationalResortUtils.peakPrice(RoomType.HUT, "7"), is(104000L));
    assertThat(NationalResortUtils.peakPrice(RoomType.HUT, "9"), is(134000L));
    assertThat(NationalResortUtils.peakPrice(RoomType.CONDO, "4"), is(68000L));
    assertThat(NationalResortUtils.peakPrice(RoomType.CONDO, "8"), is(144000L));
    assertThat(NationalResortUtils.peakPrice(RoomType.CONDO, "11"), is(164000L));
  }
}
