package com.honsul.inthewood.core.model;

import com.honsul.inthewood.core.Item;

import lombok.Data;

@Data
/**
 * 휴양림.
 */
public class Resort implements Item {
  private String resortId;
  private String resortNm;
}
