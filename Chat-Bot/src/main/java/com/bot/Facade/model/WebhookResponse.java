package com.bot.Facade.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookResponse {
    private String fulfillmentText;

    public WebhookResponse(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }

    // getters and setters
}
