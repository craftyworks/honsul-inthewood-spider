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
public class SlackMessage implements TokenBarer {
  private String token;
  
  private String channel;
  
  private String text;
  
  @JsonProperty("as_user")
  private boolean asUser;
  
  private String username;
  
  @JsonProperty("icon_emoji")
  private String iconEmoji;
  
  @JsonProperty("icon_url")
  private String iconUrl;
  
  @JsonProperty("bot_id")
  private String botId;
  
  private String type;
  
  private String subtype;
  
  private String ts;
  
  @JsonProperty("response_type")
  private String responseType;
  
  private SlackAttachment[] attachments;

  public String getText() {
    if(this.text == null) {
      return this.text;
    }
    return this.text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }

  public static SlackMessage of(String token, String channel, String text) {
    return builder().token(token).channel(channel).text(text).build();
  }
}
