package com.aqa.kb;



public class Triple {

    Object[] triple;

    public Triple(String key, String relation) {
        this.triple = new Object[3];
        this.triple[0] = key;
        this.triple[1] = relation;
    }

    public Triple(String key, String relation, double value) {
        this(key, relation);
        this.triple[2] = value; 
    }

    public Triple(String key, String relation, String value) {
        this(key, relation);
        this.triple[2] = value; 
    }

    public Object[] getTriple() {
        return triple;
    }

}
