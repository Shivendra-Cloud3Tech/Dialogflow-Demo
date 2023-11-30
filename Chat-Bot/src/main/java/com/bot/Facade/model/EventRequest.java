package com.bot.Facade.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventRequest {

    private String type;
    private String subtype;
    private String ts;
    private String text;
    private String event_ts;
    private String user;

}