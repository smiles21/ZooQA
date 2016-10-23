package com.aqa.kb;

import com.aqa.kb.Document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DocumentStore {

    /**
     * Flag to print extra information and exactly what is happening.
     */
    private boolean explicit;

    /**
     * Flag to print statistics about what is being stored in the documents.
     */
    private boolean stats;

    /**
     * The list of the Documents being stored.
     */
    private ArrayList<Document> docList;
 
    /**
     * Sets the instance variables for this DocumentStore
     */
    public DocumentStore(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;

        docList = new ArrayList<Document>();
    }

    public void addDocument(Document d) {
        this.docList.add(d);
    }
}
