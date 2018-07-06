package com.honsul.inthewood.core.model;

import java.time.LocalDate;
import java.util.Objects;

import com.honsul.inthewood.core.SpiderContext;

import lombok.Data;

/**
 * 예약 가능한 휴양림 숙소정보.
 */
@Data
public class Booking {
  private String resortId;
  private String resortNm;
  private LocalDate bookingDt;
  private String roomNm;
  
  public static Booking of(LocalDate bookingDt, String roomNm) {
    Booking booking = new Booking();
    booking.setResortId(SpiderContext.getResortId());
    booking.setBookingDt(bookingDt);
    booking.setRoomNm(roomNm);
    return booking;
  }
  
  @Override
  public boolean equals(Object obj) {
    if(obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Booking booking = (Booking) obj;
    return Objects.equals(resortId, booking.getResortId()) && Objects.equals(bookingDt, booking.getBookingDt()) && Objects.equals(roomNm, booking.getRoomNm());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(resortId, bookingDt, roomNm);
  }    
}
