package com.aqa.kb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
     * The title of the file currently being stored.
     */
    private String currentTitle;

    /**
     * The header of the section currently being stored.
     */
    private String currentHeader;
    
    /**
     * The subheader of the section of the file currently being stored.
     */
    private String currentSubheader;
    


/*
    // Unfortunately had to hard code the file names
    private String[] filenames = {
        "/corpus/american-crocodile.txt", "/corpus/cuban-crocodile.txt",
        "/corpus/freshwater-crocodile.txt", "/corpus/morelets-crocodile.txt",
        "/corpus/mugger-crocodile.txt", "/corpus/new-guinea-crocodile.txt",
        "/corpus/nile-crocodile.txt", "/corpus/orinoco-crocodile.txt",
        "/corpus/phillipine-crocodile.txt", "/corpus/saltwater-crocodile.txt",
        "/corpus/siamese-crocodile.txt", "/corpus/west-african-crocodile.txt"
    };
*/

    // This is just to test the file-reading and knowledge base construction capabilities on only one file.
    private String[] filenames = {"/corpus/nile-crocodile.txt"};
    
    /**
     * Sets the instance variables for this DocumentStore
     */

    public DocumentStore(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;
    }

    public void loadDocuments() {
        System.out.println("We need code to load the documents");

        try {        
            ClassLoader classLoader = this.getClass().getClassLoader();
            for (String filename : filenames){

                InputStream in = getClass().getResourceAsStream(filename);
                Scanner scan = new Scanner(in);
                
                String currentLine = null;
                while(scan.hasNextLine()) {
                    currentLine = scan.nextLine();

                    isTaggedLine(currentLine);
           
                    if(explicit)
                        System.out.printf("Title: %s, Header: %s, Subheader: %s\n", currentTitle, currentHeader, currentSubheader);
 
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println();


    }

    /**
     * Checks if line starts with a title, header, or subheader tag.
     *  If so, sets the appropriate instance variable.
     */
    private boolean isTaggedLine(String line) {
        if(line.startsWith("<title>")){
            currentTitle = line.substring(7);
            return true;
        }
        else if(line.startsWith("<header>")){
            currentHeader = line.substring(8);
            return true;
        }
        else if(line.startsWith("<subheader>")){
            currentSubheader = line.substring(11);
            return true;
        }
        else {
            return false;
        }
        
    }

}
