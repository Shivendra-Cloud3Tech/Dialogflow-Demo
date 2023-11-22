package com.bot.controller;


import com.bot.model.*;
import com.bot.repository.RegisterComplainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Autowired
    RegisterComplainRepo registerComplainRepo;

    @PostMapping("/webhook")
    public WebhookResponse handleWebhook(@RequestBody WebhookRequest webhookRequest) {
//        String intent = webhookRequest.getQueryResult().getIntent();
        String sessionId = webhookRequest.getQueryResult().getSession();
        QueryResult queryResult=webhookRequest.getQueryResult();
       
//       OutputContext outputContext= webhookRequest.getQueryResult().setSession();
        Parameters parameter=queryResult.getParameters();
        Intent intent=queryResult.getIntent();
        String intentname= intent.getDisplayName();

        switch (intentname){

//         if (intentname.equals(intentname)){
            case "complain":
             String add= parameter.getAddress();
             String issue= parameter.getIssueType();
             System.out.println("issue name"+issue);
             RegisterComplain regcomp= new RegisterComplain();
             regcomp.setAddress(add);
             regcomp.setIssueType(issue);
             registerComplainRepo.save(regcomp);

             return new WebhookResponse("Your complain have been register successfully");

            case "ReportIssue":
                String issue1 = parameter.getIssue();
                System.out.println("issue name"+issue1);
                return new WebhookResponse( "Can you please provide the address where you found the pothole?");

            case "Get-Address":
                String add1= parameter.getAddress();
                System.out.println(add1);
                return new WebhookResponse(" Got it. Can you tell me where the pothole is? Is it close to the crosswalk, along the curb lane, at the intersection, or within the traffic lane?");

            case  "Get-loaction":
                String location = parameter.getIssuelocation();
                System.out.println(location);
                return new WebhookResponse("I appreciate your help. Can you please supply additional information?");


                    default:
                return new WebhookResponse("complain unreachable");

        }


//       return new WebhookResponse("complain unreachable");
    }



}

