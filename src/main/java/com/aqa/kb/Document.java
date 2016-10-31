package com.aqa.kb;

import java.util.ArrayList;

public class Document {
    
    /**
     * The title of the document.
     */
    private String title;

    /**
     * The full text of the document.
     */
    private String text;

    /**
     * The full text of the document, each sentence getting its own index.
     */
    private ArrayList<String> sentences;    


    public Document() {
        this.sentences = new ArrayList<String>();
    }

    public String getTitle() {
        return this.title;
    }
    
    public String getText() {
        return this.text;
    }

    public String getSentence(int index) {
        return this.sentences.get(index);
    }

    public void setText(String t) {
        this.text = t;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void addSentence(int index, String s) {
        this.sentences.add(index, s);
    }

}
