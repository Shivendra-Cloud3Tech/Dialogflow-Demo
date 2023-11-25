package com.bot.config;

// SlackConfig.java
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SlackConfig {
    @Value("${slack-single-team-bot-token}")
    private String slackSingleTeamBotToken;

    @Value("${slack-signing-secret}")
    private String slackSigningSecret;

    @Bean
    public App initSlackApp() {
        var config = new AppConfig();
        config.setSingleTeamBotToken(slackSingleTeamBotToken);
        config.setSigningSecret(slackSigningSecret);
        return new App(config);
    }
}
