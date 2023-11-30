

# Dialogflow Demo

Dialogflow Demo is a Spring Boot project designed to interact with Google Dialogflow, utilizing the following technologies:

- Spring Boot version: 3.0.12
- Java version: 17
- Google Cloud Dialogflow version: 4.9.1
- MySQL version: 8

The project aims to send requests to Google Dialogflow and receive responses, showcasing the integration capabilities of Dialogflow within a Spring Boot environment.

## Project Structure

```plaintext
dialogflow-demo/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.bot/
│   │   │       └── Fascade/
│   │   │           └── config/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│                       │── service/
│   │   │               └── ChatBotApplication.java
│   │   │
│   │   ├── resources/
│   │      ├── application.properties
│   │      └── google-key/
│   │         └── your-google-credentials.json
│   │   
│   │   
│   │
│   ├── test/
│   │
├── target/
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── README.md
└── pom.xml
