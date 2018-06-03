package com.honsul.inthewood.core.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Room {
  private String resortId;
  private String resortNm;
  private String roomNm;
  private RoomType roomType;
  private String numberOfPeople;
  private String space;
  private String info;
  long price;
  long peakPrice;
  LocalDate insertDt;
  LocalDate updateDt;
}
