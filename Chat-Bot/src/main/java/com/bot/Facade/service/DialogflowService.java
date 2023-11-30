package com.bot.Facade.service;


import com.bot.Facade.model.WebhookRequest;
import com.bot.Facade.model.WebhookResponse;

import java.io.IOException;

public interface DialogflowService {

    String postMessageToDialogflow(String essage, String sessionId) throws IOException;
    public WebhookResponse handleWebhook(WebhookRequest webhookRequest);
}