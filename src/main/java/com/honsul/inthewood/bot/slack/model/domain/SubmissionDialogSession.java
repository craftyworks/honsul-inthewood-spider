package com.honsul.inthewood.bot.slack.model.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubmissionDialogSession {
  @JsonProperty("submission_id")
  private String submissionId;
  
  @JsonProperty("subscription_id")
  private String subscriptionId;
  
  @JsonProperty("callback_url")
  private String callbackUrl;
  
  private SubmissionDialogSession() {
    
  }
  
  public static SubmissionDialogSession of(String callbackUrl, String subscriptionId) {
    SubmissionDialogSession session = new SubmissionDialogSession();
    session.setSubmissionId(UUID.randomUUID().toString());
    session.setSubscriptionId(subscriptionId);
    session.setCallbackUrl(callbackUrl);
    return session;
  }
  
  public static SubmissionDialogSession of(String callbackUrl) {
    return of(callbackUrl, "");
  }
  
}
