package com.bot.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SlackRequest {
//    private String text;
    private String challenge;
    private EventRequest event;

}
