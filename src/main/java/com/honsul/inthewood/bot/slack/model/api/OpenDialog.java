package com.honsul.inthewood.bot.slack.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.honsul.inthewood.bot.slack.model.SlackDialog;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OpenDialog {
  private String token;
  private SlackDialog dialog;
  @JsonProperty("trigger_id")
  private String triggerId;
}
