package com.honsul.inthewood.bot.slack.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ImChannel {
  private String id;
  @JsonProperty("is_im")
  private boolean isIm;
  private String user;
  private String created;
  @JsonProperty("is_user_deleted")
  private boolean isUserDeleted;
}
