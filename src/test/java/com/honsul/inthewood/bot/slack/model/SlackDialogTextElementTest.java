package com.honsul.inthewood.bot.slack.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.honsul.inthewood.bot.slack.model.SlackDialogElement.Type;
import com.honsul.inthewood.bot.slack.model.SlackDialogTextElement.SubType;

public class SlackDialogTextElementTest {
  
  private static final Logger logger = LoggerFactory.getLogger(SlackDialogTextElementTest.class);

  @Test
  public void testTextElement() throws JsonProcessingException {
    SlackDialogTextElement element = SlackDialogTextElement.builder()
        .type(Type.text)
        .label("label")
        .name("name")
        .value("value")
        .placeholder("placeholder")
        .optional(false)
        .maxLength(1000)
        .minLength(0)
        .hint("hint")
        .subtype(SubType.email).build();
    
    assertThat(element.getType(), is(Type.text));
    assertThat(element.getLabel(), is("label"));
    assertThat(element.isOptional(), is(false));
    assertThat(element.getSubtype(), is(SubType.email));
  }

}