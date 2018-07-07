package com.honsul.inthewood.bot.slack.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlackDialog {
    private String title;
    @JsonProperty("callback_id")
    private String callbackId;
    private List<SlackDialogElement> elements;
    @JsonProperty("submit_label")
    private String submitLabel;
    @JsonProperty("notify_on_cancel")
    private boolean notifyOnCancel;
    
    public SlackDialog addElement(SlackDialogElement element) {
      this.elements.add(element);
      return this;
    }

    @Builder
    public SlackDialog(String title, String callbackId, List<SlackDialogElement> elements, String submitLabel, boolean notifyOnCancel) {
      this.title = title;
      this.callbackId = callbackId;
      this.elements = elements != null ? elements : new ArrayList<>();
      this.submitLabel = submitLabel;
      this.notifyOnCancel = notifyOnCancel;
    }
    
}
