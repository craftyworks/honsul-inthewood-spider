package com.honsul.inthewood.core.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.honsul.inthewood.core.SpiderContext;

public class BookingTest {

  @Test
  public void testOf() {
    SpiderContext.setResortId("TEST");
    Booking a = Booking.of(LocalDate.now(), "1번방");
    
    assertThat(a.getResortId(), is(SpiderContext.getResortId()));
    
    assertThat(a.getBookingDt(), is(LocalDate.now()));
    
    assertThat(a.getRoomNm(), is("1번방"));
  }

}
