package com.bot.model;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowController {
    private final String projectId = "omega-scope-404304";

    @PostMapping("/hello")
    public String sendHelloRequest(@RequestBody Message message, @RequestParam("sessionId") String sessionId) throws Exception {
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(
                        getClass().getClassLoader().getResourceAsStream("googlekey/omega-scope-404304-d71d8292488d.json"))))
                .build();

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            SessionName session = SessionName.of(projectId, sessionId);
            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(TextInput.newBuilder().setText(message.getMessage()).setLanguageCode("en-US"))
                    .build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

            QueryResult queryResult = response.getQueryResult();
            System.out.println(queryResult);
            return queryResult.getFulfillmentText();
        }
    }
}
