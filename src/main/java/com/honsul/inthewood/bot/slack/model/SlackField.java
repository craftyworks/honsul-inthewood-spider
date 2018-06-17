package com.honsul.inthewood.bot.slack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlackField {
    private String title;
    private String value;
    @JsonProperty("short_enough")
    private boolean shortEnough;
    @JsonProperty("short")
    private boolean shortField;
}
