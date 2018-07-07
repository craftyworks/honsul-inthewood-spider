package com.honsul.inthewood.bot.slack.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.honsul.inthewood.bot.slack.model.SlackDialogElement.Type;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.OptionGroup;

public class SlackDialogSelectElementTest {
  
  @Test
  public void testSelectElement() throws JsonProcessingException {
    SlackDialogSelectElement element = SlackDialogSelectElement.builder()
        .label("label")
        .name("name")
        .value("value")
        .placeholder("placeholder")
        .optional(false)
        .minQueryLength(2)
        .build();
    
    assertThat(element.getType(), is(Type.select));
    assertThat(element.getLabel(), is("label"));
    assertThat(element.isOptional(), is(false));
    assertThat(element.getMinQueryLength(), is(2));
    
    element.addOption(Option.of("취소", "cancel"));
    element.addOption(Option.of("확인", "ok"));
    
    assertThat(element.getOptions().size(), is(2));
    assertThat(element.getOptions().get(0).getValue(), is("cancel"));
    
    element.addOptionGroup(OptionGroup.of("저장").addOption(Option.of("확인", "ok")).addOption(Option.of("취소", "cancel")));
    element.addOptionGroup(OptionGroup.of("도움말").addOption(Option.of("확인", "ok")).addOption(Option.of("취소", "cancel")));
    
    assertThat(element.getOptionGroups().size(), is(2));
    assertThat(element.getOptionGroups().get(1).getLabel(), is("도움말"));
  }
}
