package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class SlackAction {
  private String id;
  
  private String name;
  
  private String text;
  
  private String type;
  
  private String value;
  
  private String style;
  
  private Confirm confirm;
  
  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  public static class Confirm {
    private String title;
    private String text;
    @JsonProperty("ok_text")
    private String okText;
    @JsonProperty("dismiss_text")
    private String dismissText;
  }
}
