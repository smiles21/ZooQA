package com.aqa.kb;

import com.aqa.kb.Document;

public class Triple {

    /**
     * This is where the stuff is actually stored
     */
    private Object[] triple;

    /**
     * The Document which contains this Triple
     */
    private Document containingDoc;

    /**
     * The sentence number for this Triple.
     *  Can be used to index the sentence and retrieve the actual text.
     */
    private int sentenceNumber;

    public Triple(String key, String relation) {
        this.triple = new Object[3];
        this.triple[0] = key;
        this.triple[1] = relation;
    }

    public Triple(String key, String relation, double value, Document doc, int num) {
        this(key, relation);
        this.triple[2] = value;
        this.containingDoc = doc;
        this.sentenceNumber = num;
    }

    public Triple(String key, String relation, String value, Document doc, int num) {
        this(key, relation);
        this.triple[2] = value; 
        this.containingDoc = doc;
        this.sentenceNumber = num;
    }

    public boolean hasSubject(String subject) {
        return triple[0].equals(subject);
    }
    
    public boolean hasRelation(String relation) {
        return triple[1].equals(relation);
    }

    public Document getDocument() {
        return this.containingDoc;
    }

    public int getSentenceNumber() {
        return this.sentenceNumber;
    }

    public Object[] getTriple() {
        return this.triple;
    }

    public String toString() {
        return "[" + this.triple[0] + ", " + this.triple[1] + ", " + this.triple[2] + "]";
    }

}
