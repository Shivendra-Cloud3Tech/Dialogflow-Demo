package com.bot.Facade.service;

import com.bot.Facade.model.SlackRequest;
import com.bot.Facade.model.SlackResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface SlackService {
    public SlackResponse handleSlackEvent(SlackRequest slackRequest) throws IOException;
}
