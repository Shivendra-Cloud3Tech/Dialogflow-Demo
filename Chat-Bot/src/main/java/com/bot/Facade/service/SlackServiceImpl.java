package com.bot.Facade.service;

import com.bot.Facade.model.SlackRequest;
import com.bot.Facade.model.SlackResponse;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.SlackApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
@Slf4j
public class SlackServiceImpl extends SlackAppServlet implements SlackService {
    @Autowired
    DialogflowService dialogflowService;
    private String sessionId="12345678";

    @Value("${slack-bot-token}")
    private String slackBotToken;

    public SlackServiceImpl(App app) {
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

    @Override
    public SlackResponse handleSlackEvent(SlackRequest slackRequest) throws IOException {
        if(slackRequest.getEvent() == null) {
            return new SlackResponse(slackRequest.getChallenge());
        }
        else {
            String message=slackRequest.getEvent().getText();
            this.publishMessage(slackRequest.getEvent().getUser(),dialogflowService.postMessageToDialogflow(message,sessionId));
            return new SlackResponse(dialogflowService.postMessageToDialogflow(message,sessionId));
        }

    }
    }

