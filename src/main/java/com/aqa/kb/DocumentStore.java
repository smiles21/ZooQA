package com.aqa.kb;

public class DocumentStore {

    /*
     * Flags to print extra information and show statistics
     */
    private boolean explicit, stats;
    
    public DocumentStore(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;
    }

    public void loadDocuments() {
        System.out.println("We need code to load the documents");
        // Need to add code to read in the files here and load them 
        //  into a data structure that can handle all their information.
    }

}
