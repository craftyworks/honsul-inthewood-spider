package com.honsul.inthewood.core.model;

import com.honsul.inthewood.core.Item;

import lombok.Data;

@Data
public class Hotel implements Item {
  private String hotelId;
  private String hotelNm;
}
