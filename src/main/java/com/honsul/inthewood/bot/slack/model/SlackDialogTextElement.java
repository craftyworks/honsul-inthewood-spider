package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlackDialogTextElement extends SlackDialogElement {
  private Type type; 
  
  @JsonProperty("max_length")
  private int maxLength;
  
  @JsonProperty("min_length")
  private int minLength;
  
  private String hint;
  
  private SubType subtype;
  
  public static enum SubType {
    email, number, tel, url;  
  }

  @Builder
  public SlackDialogTextElement(String label, String name, String value, String placeholder, boolean optional,
      Type type, int maxLength, int minLength, String hint, SubType subtype) {
    super(label, name, value, placeholder, optional);
    this.type = type;
    this.maxLength = maxLength;
    this.minLength = minLength;
    this.hint = hint;
    this.subtype = subtype;
  }

}
