package com.bot.Facade.controller;



import com.bot.Facade.model.WebhookRequest;
import com.bot.Facade.model.WebhookResponse;
import com.bot.Facade.service.DialogflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DialogflowController {
    private final DialogflowService dialogflowService;

    public DialogflowController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    private final String projectId = "omega-scope-404304";

    @PostMapping("/api/dialogflow")
    public ResponseEntity<Object> sendRequest(@RequestBody String message, @RequestParam("sessionId") String sessionId) throws Exception {
        return ResponseEntity.ok(dialogflowService.postMessageToDialogflow(message,sessionId));
    }

    @PostMapping("/webhook")
    public ResponseEntity<WebhookResponse> handleWebhook(@RequestBody WebhookRequest webhookRequest){
        return ResponseEntity.ok(dialogflowService.handleWebhook(webhookRequest));

    }



}
