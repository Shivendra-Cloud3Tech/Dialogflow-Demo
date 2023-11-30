package com.bot.Facade.service;

import com.bot.Facade.model.Parameters;
import com.bot.Facade.model.RegisterComplain;
import com.bot.Facade.repository.RegisterComplainRepo;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DialogflowServiceImpl implements DialogflowService {
    private final String projectId = "omega-scope-404304";
    public String address=null;
    public String issuety=null;

    public String location=null;

    public String detail=null;

    public String request=null;





    @Autowired
    RegisterComplainRepo registerComplainRepo;


    public void saveinfo(String address, String issuety ,String location,String detail,String request ){

        RegisterComplain registerComplain= new RegisterComplain();
        registerComplain.setAddress(address);
        registerComplain.setIssueType(issuety);
        registerComplain.setLocation(location);
        registerComplain.setDetails(detail);
        registerComplain.setRequestType(request);
        registerComplainRepo.save(registerComplain);

    }


    @Override
    public String postMessageToDialogflow(String message, String sessionId) throws IOException {

            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(
                            getClass().getClassLoader().getResourceAsStream("googlekey/omega-scope-404304-d71d8292488d.json"))))
                    .build();

            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
                SessionName session = SessionName.of(projectId, sessionId);
                QueryInput queryInput = QueryInput.newBuilder()
                        .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US"))
                        .build();
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                QueryResult queryResult = response.getQueryResult();
                System.out.println(queryResult);
                return queryResult.getFulfillmentText();
            }
        }

    @Override
    public com.bot.Facade.model.WebhookResponse handleWebhook(com.bot.Facade.model.WebhookRequest webhookRequest) {

        String sessionId = webhookRequest.getQueryResult().getSession();
        com.bot.Facade.model.QueryResult queryResult=webhookRequest.getQueryResult();
        Parameters parameter=queryResult.getParameters();
        com.bot.Facade.model.Intent intent=queryResult.getIntent();
        String intentname= intent.getDisplayName();



        switch (intentname){
            case "complain":
                String add= parameter.getAddress();
                String issue= parameter.getIssueType();
                System.out.println("issue name"+issue);
                RegisterComplain regcomp= new RegisterComplain();
                regcomp.setAddress(add);
                regcomp.setIssueType(issue);
                registerComplainRepo.save(regcomp);

                return new com.bot.Facade.model.WebhookResponse("Your complain have been register successfully");

            case "start-convo":
                String req= parameter.getRequest();
                request=req;
                return new com.bot.Facade.model.WebhookResponse( "Choose the request type: Pothole, tree debris, street light out, or graffiti.");

            case "ReportIssue":
                String issue1 = parameter.getIssue();
                System.out.println("issue name"+issue1);
                issuety=issue1;
                return new com.bot.Facade.model.WebhookResponse( "Can you please provide the address where you found the pothole?");

            case "Get-Address":
                String add1= parameter.getAddress();
                System.out.println(add1);
                address=add1;
                return new com.bot.Facade.model.WebhookResponse(" Got it. Can you tell me where the pothole is? Is it close to the crosswalk, along the curb lane, at the intersection, or within the traffic lane?");

            case  "Get-loaction":
                String location1 = parameter.getIssuelocation();
                System.out.println(location1);
                location=location1;
//                this.saveinfo(address,issuety,location);
                return new com.bot.Facade.model.WebhookResponse("I appreciate your help. Can you please supply additional information?");

            case "add-info":
                String message= queryResult.getQueryText();
                detail=message;

                this.saveinfo(address,issuety,location,detail,request);
                return new com.bot.Facade.model.WebhookResponse("Do you have any picture to share");


            case "add-picture":
                return new com.bot.Facade.model.WebhookResponse("Your complaint has been successfully registered. Thank you for bringing this matter to our attention.");



        }






        return new com.bot.Facade.model.WebhookResponse("complain unreachable");
    }

    }

