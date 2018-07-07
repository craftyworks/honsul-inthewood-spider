package com.honsul.inthewood.bot.slack.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SlackDialogElement {
    private String label;
    private String name;
    private String value;    
    private String placeholder;
    private boolean optional;
    
    public static enum Type {
      text, textarea, select;
    }
}
