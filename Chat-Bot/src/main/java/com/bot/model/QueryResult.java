package com.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResult {
    private Intent intent;
    private Parameters parameters;
    private String session;
    private OutputContexts outputContext;

    // getters and setters
}

