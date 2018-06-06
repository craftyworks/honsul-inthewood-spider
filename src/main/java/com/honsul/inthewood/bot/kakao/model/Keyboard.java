package com.honsul.inthewood.bot.kakao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Keyboard {
  private String type;
  private String[] buttons;
  
  public static Keyboard of(String type) {
    Keyboard keyboard = new Keyboard();
    keyboard.setType(type);
    keyboard.setButtons(new String[] {});
    return keyboard;
  }
}
