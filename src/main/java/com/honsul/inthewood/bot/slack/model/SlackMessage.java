package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlackMessage {
  private String username;
  
  @JsonProperty("icon_emoji")
  private String iconEmoji;
  
  private String channel;
  
  private String text;
  
  @JsonProperty("response_type")
  private String responseType;
  
  private SlackAttachment[] attachments;
}
