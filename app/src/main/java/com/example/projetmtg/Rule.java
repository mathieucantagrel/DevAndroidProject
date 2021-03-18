package com.example.projetmtg;

import java.io.Serializable;

public class Rule implements Serializable {

    private final String date;
    private final String rule;

    public Rule(String date, String rule) {
        this.date = date;
        this.rule = rule;
    }

    public String getDate() {
        return date;
    }

    public String getRule() {
        return rule;
    }
}
