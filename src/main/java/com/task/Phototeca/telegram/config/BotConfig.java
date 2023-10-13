package com.task.phototeca.telegram.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    @Value("${telegram.bot.bot-name}")
    String botName;

    @Value("${telegram.bot.token}")
    String token;

}