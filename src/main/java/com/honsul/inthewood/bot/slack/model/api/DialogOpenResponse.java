package com.honsul.inthewood.bot.slack.model.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DialogOpenResponse {
  private boolean ok;
  private String error;
  @JsonProperty("response_metadata")
  private ResponseMetadata responseMetadata;
  
  @Data
  public static class ResponseMetadata{
    private List<String> messages;
  }
}

