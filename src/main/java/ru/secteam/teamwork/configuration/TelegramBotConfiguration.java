package ru.secteam.teamwork.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("application.properties")
public class TelegramBotConfiguration {
    @Value("${telegram.bot.name}")
    String botName;

    @Value("${telegram.bot.token}")
    String token;

}
