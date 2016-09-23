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

    public void loadDocuments(String[] filenames) {
        System.out.print("Loading Documents... ");

        try {
            // Load each file using the ClassLoader
            ClassLoader classLoader = this.getClass().getClassLoader();
            for (String filename : filenames){
                InputStream in = getClass().getResourceAsStream(filename);
                Scanner scan = new Scanner(in);
                
                StringBuilder builder = new StringBuilder();
                String currentLine = null;
                String title = null;
                while(scan.hasNextLine()) {
                    currentLine = scan.nextLine();

                    if(currentLine.startsWith("<title>"))
                        title = currentLine.substring(7);
                    
                    builder.append(currentLine);

                }
                docList.add(new Document(title, builder.toString()));

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loading Documents Complete\n");
    
        if(explicit)
            System.out.printf("[EXPLICIT] Stored %d documents in the document store \n", docList.size());
    }


}
