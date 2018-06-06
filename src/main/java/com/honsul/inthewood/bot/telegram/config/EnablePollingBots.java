package com.honsul.inthewood.bot.telegram.config;

import org.springframework.context.annotation.Import;

@Import(PollingBotStarterConfiguration.class)
public @interface EnablePollingBots {
}