package com.honsul.inthewood.core.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Subscriber {
  private String subscriberId;
  private String subscriberType;
  private LocalDate bookingDt;
  private String resortId;
  private RoomType roomType;
}
