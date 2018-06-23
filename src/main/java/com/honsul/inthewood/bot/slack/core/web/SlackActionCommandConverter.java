package com.honsul.inthewood.bot.slack.core.web;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;

@Component
public class SlackActionCommandConverter extends AbstractHttpMessageConverter<SlackActionCommand> {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
  private static final ObjectMapper mapper = new ObjectMapper();

  public SlackActionCommandConverter() {
    super(new MediaType("application","x-www-form-urlencoded", Charset.forName("UTF-8")));
  }
  
  @Override
  protected boolean supports(Class<?> clazz) {
    return (SlackActionCommand.class == clazz);
  }

  @Override
  protected SlackActionCommand readInternal(Class<? extends SlackActionCommand> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    
    return mapper.readValue(formHttpMessageConverter.read(null, inputMessage).getFirst("payload"), SlackActionCommand.class);
  }

  @Override
  protected void writeInternal(SlackActionCommand slackActionCommand, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {

  }
}
