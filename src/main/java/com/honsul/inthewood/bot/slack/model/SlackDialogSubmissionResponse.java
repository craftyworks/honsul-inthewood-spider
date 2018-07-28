package com.honsul.inthewood.bot.slack.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SlackDialogSubmissionResponse implements SlackActionCommandResponsable {
  private List<Error> errors;
  
  @Data
  public static class Error {
    private String name;
    private String error;
  }
  
  private SlackDialogSubmissionResponse() {
    
  }
  
  public static SlackDialogSubmissionResponse ok() {
    return new SlackDialogSubmissionResponse();
  }
  
  public static SlackDialogSubmissionResponse error(Error... err) {
    SlackDialogSubmissionResponse res = new SlackDialogSubmissionResponse();
    res.errors = new ArrayList<>();
    for(Error e : err) {
      res.errors.add(e);
    }
    return res;
  }
}
