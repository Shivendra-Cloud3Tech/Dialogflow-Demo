package com.bot.Facade.controller;

import com.bot.Facade.model.SlackRequest;
import com.bot.Facade.model.SlackResponse;
import com.bot.Facade.service.DialogflowService;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.dialogflow.v2.*;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.SlackApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/accident/tracker/channel")
public class SlackBotController  {
//extends SlackAppServlet

    @Autowired
    DialogflowService dialogflowService;
    private final String projectId = "omega-scope-404304";
    private String sessionId="12345678";


    @PostMapping("/webhook/slack")
    public  ResponseEntity<SlackResponse> handleSlackEvent(@RequestBody SlackRequest slackRequest) throws Exception {

        if(slackRequest.getEvent() == null) {
            return ResponseEntity.ok(new SlackResponse(slackRequest.getChallenge()));
        }
        else {
        String message=slackRequest.getEvent().getText();

        return ResponseEntity.ok(new SlackResponse(dialogflowService.postMessageToDialogflow(message,sessionId)));
        }

    }
}
