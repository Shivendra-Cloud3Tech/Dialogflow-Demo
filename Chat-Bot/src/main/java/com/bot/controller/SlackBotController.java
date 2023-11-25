package com.bot.controller;

import com.bot.model.SlackRequest;
import com.bot.model.SlackResponse;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.dialogflow.v2.*;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.SlackApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/accident/tracker/channel")
public class SlackBotController extends SlackAppServlet {
//extends SlackAppServlet
    private final String projectId = "omega-scope-404304";
    private String sessionId="12345678";

    @Value("${slack-bot-token}")
    private String slackBotToken;

    public SlackBotController(App app) {
        super(app);
    }


    private void publishMessage(String id, String text) {
        if(text == null) {
            log.warn("Text is mandatory");
            return;
        }
        var client = Slack.getInstance().methods();
        try {
            log.info("publish message to Slack. id: " + id + ", text: " + text);
            var result = client.chatPostMessage(r -> r
                    .token(slackBotToken)
                    .channel(id)
                    .username(id)
                    .text(text)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }
    @PostMapping("/webhook/slack")
    public  ResponseEntity<SlackResponse> handleSlackEvent(@RequestBody SlackRequest slackRequest) throws Exception {

        if(slackRequest.getEvent() == null) {
            return ResponseEntity.ok(new SlackResponse(slackRequest.getChallenge()));
        }

        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(
                        getClass().getClassLoader().getResourceAsStream("googlekey/omega-scope-404304-d71d8292488d.json"))))
                .build();

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            SessionName session = SessionName.of(projectId, sessionId);
            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(TextInput.newBuilder().setText(slackRequest.getEvent().getText()).setLanguageCode("en-US"))
                    .build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

            QueryResult queryResult = response.getQueryResult();
            System.out.println(queryResult);
//             new SlackResponse(queryResult.getFulfillmentText());
            return ResponseEntity.ok(new SlackResponse(queryResult.getFulfillmentText()));
        }
//        return null;
    }
}
