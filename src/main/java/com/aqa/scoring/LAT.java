package com.aqa.scoring;

/**
 * The possible lexical answer types supported by ZooQA.
 *  This mirror the categories of Triples extracted by the system.
 */ 
public enum LAT {
    LENGTH("has-length"),
    WEIGHT("has-weight"),
    HABITAT("has-habitat"),
    LOCATION("has-location");

    private String relation;

    LAT(String r) {
        this.relation = r;
    }
    
    public String relation(){
        return this.relation;
    }
}
