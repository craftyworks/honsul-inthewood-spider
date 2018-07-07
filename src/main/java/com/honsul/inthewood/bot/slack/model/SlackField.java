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
public class SlackField {
    private String title;
    private String value;
    @JsonProperty("short_enough")
    private boolean shortEnough;
    @JsonProperty("short")
    private boolean isShort;
    
    public static SlackField of(String title, String value) {
      return SlackField.builder().title(title).value(value).isShort(true).build();
    }
    
    public static SlackField of(String title, String value, boolean isShort) {
      return SlackField.builder().title(title).value(value).isShort(isShort).build();
    }
}
