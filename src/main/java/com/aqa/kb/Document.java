package com.aqa.kb;

class Document {
    
    /**
     * The title of the document.
     */
    private String title;

    /**
     * The text of the document.
     */
    private String text;
    
    public Document(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }
    
    public String getText() {
        return this.text;
    }


}
