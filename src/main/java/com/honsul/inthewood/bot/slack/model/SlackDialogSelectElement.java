package com.honsul.inthewood.bot.slack.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SlackDialogSelectElement extends SlackDialogElement {
  private final Type type = Type.select; 
  
  @JsonProperty("data_source")
  private DataSourceType dataSource;
  
  @JsonProperty("min_query_length")
  private int minQueryLength;
  
  private List<Option> options;

  @JsonProperty("option_groups")
  private List<OptionGroup> optionGroups;
  
  @JsonProperty("selected_options")
  private List<Option> selectedOptions;
  
  public static enum DataSourceType {
    users, channels, conversations, external;
  }
  
  public SlackDialogSelectElement addOption(Option o) {
    if(this.options == null) {
      this.options = new ArrayList<>();
    }
    this.options.add(o);
    return this;
  }
  
  public SlackDialogSelectElement addOptionGroup(OptionGroup og) {
    if(this.optionGroups == null) {
      this.optionGroups = new ArrayList<>();
    }    
    this.optionGroups.add(og);
    return this;
  }
  
  public SlackDialogSelectElement addSelectedOption(Option o) {
    if(this.selectedOptions == null) {
      this.selectedOptions = new ArrayList<>();
    }        
    this.selectedOptions.add(o);
    return this;
  }
  
  @Data
  @AllArgsConstructor
  public static class Option {
    private String label;
    private String value;
    public static Option of(String label, String value) {
      return new Option(label, value);
    }
  }
  
  @Data
  public static class OptionGroup {
    private String label;
    private List<Option> options;
    
    private OptionGroup(String label) {
      this.label = label;
      this.options = new ArrayList<>();
    }
    
    public static OptionGroup of(String label) {
      return new OptionGroup(label);
    }
    
    public OptionGroup addOption(Option o) {
      this.options.add(o);
      return this;
    }
  }

  @Builder
  public SlackDialogSelectElement(String label, String name, String value, String placeholder, boolean optional,
      DataSourceType dataSource, int minQueryLength, List<Option> options,
      List<OptionGroup> optionGroups, List<Option> selectedOptions) {
    super(label, name, value, placeholder, optional);
    
    this.dataSource = dataSource;
    this.minQueryLength = minQueryLength;
    this.options = options;
    this.optionGroups = optionGroups;
    this.selectedOptions = selectedOptions;
  }
}
