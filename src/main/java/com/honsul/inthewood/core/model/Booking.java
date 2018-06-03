package com.honsul.inthewood.core.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Booking {
  private String resortId;
  private String resortNm;
  private LocalDate bookingDt;
  private String roomNm;
}
