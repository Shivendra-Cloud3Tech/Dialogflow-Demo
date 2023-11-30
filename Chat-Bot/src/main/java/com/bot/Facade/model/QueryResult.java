package com.bot.Facade.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResult {
    private Intent intent;
    private Parameters parameters;
    private String session;
    private OutputContexts outputContext;
    private String queryText;

    // getters and setters
}

