package com.honsul.inthewood.bot.slack.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.OptionGroup;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SlackDialogOptionHolder  {

  private List<Option> options;

  @JsonProperty("option_groups")
  private List<OptionGroup> optionGroups;

  private SlackDialogOptionHolder() {
    
  }
  
  public static SlackDialogOptionHolder ofOptions(List<Option> options) {
    SlackDialogOptionHolder holder = new SlackDialogOptionHolder();
    holder.options = options;
    return holder;
  }
  
  public static SlackDialogOptionHolder ofOptionGroups(List<OptionGroup> optionGroups) {
    SlackDialogOptionHolder holder = new SlackDialogOptionHolder();
    holder.optionGroups = optionGroups;
    return holder;
  }
  
}
