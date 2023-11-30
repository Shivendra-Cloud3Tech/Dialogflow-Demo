package com.bot.Facade.model;

public class SlackResponse {
    private String message;

    public SlackResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
