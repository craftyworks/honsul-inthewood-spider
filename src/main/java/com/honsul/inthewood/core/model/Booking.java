package com.honsul.inthewood.core.model;

import java.time.LocalDate;

import com.honsul.inthewood.core.Item;

import lombok.Data;

@Data
public class Booking implements Item {
  private String hotelId;
  private LocalDate bookingDt;
  private String roomNm;
  private String roomNo;
}
