package com.bot.controller;


import com.bot.model.*;
import com.bot.repository.RegisterComplainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

     public String address=null;
    public String issuety=null;

    public String location=null;

    public String detail=null;

    @Autowired
    RegisterComplainRepo registerComplainRepo;

     public void saveinfo(String address, String issuety ,String location,String detail ){

        RegisterComplain registerComplain= new RegisterComplain();
        registerComplain.setAddress(address);
        registerComplain.setIssueType(issuety);
        registerComplain.setLocation(location);
        registerComplain.setDetails(detail);
        registerComplainRepo.save(registerComplain);

    }

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
                issuety=issue1;
                return new WebhookResponse( "Can you please provide the address where you found the pothole?");

            case "Get-Address":
                String add1= parameter.getAddress();
                System.out.println(add1);
                address=add1;
                return new WebhookResponse(" Got it. Can you tell me where the pothole is? Is it close to the crosswalk, along the curb lane, at the intersection, or within the traffic lane?");

            case  "Get-loaction":
                String location1 = parameter.getIssuelocation();
                System.out.println(location1);
                location=location1;
//                this.saveinfo(address,issuety,location);
                return new WebhookResponse("I appreciate your help. Can you please supply additional information?");

                 case "add-info":
               String message= queryResult.getQueryText();
               detail=message;

                this.saveinfo(address,issuety,location,detail);
                return new WebhookResponse("Do you have any picture to share");


            case "add-picture":
                return new WebhookResponse("Your complaint has been successfully registered. Thank you for bringing this matter to our attention.");



                 

        }


   return new WebhookResponse("complain unreachable");
    }



}

