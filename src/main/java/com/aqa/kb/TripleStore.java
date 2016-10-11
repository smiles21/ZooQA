package com.aqa.kb;

import com.aqa.kb.Triple;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TripleStore {


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
     * The store of all the triples we are storing.
     *   The primary key is the for all information regarding the key.  
     */
    private Map<String, Collection<Triple>> tripleMap;

    public TripleStore(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;

        this.tripleMap = new HashMap<String, Collection<Triple>>();
    }

    public void createTriples() {
        System.out.print("Creating Triples... ");

        try {
            for(String filename : filenames) {
                InputStream in = getClass().getResourceAsStream(filename);
                Scanner scan = new Scanner(in);

                String currentLine = null;
                while(scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    System.out.println(currentLine);
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

/*        // If the line has title, header, or subheader info, don't index it.
        //  Otherwise, store information from the sentence.
        if(isTaggedLine(currentLine)) {
            if(explicit)
                System.out.printf("[EXPLICIT] Title: %s, Header: %s, Subheader: %s\n", currentTitle, currentHeader, currentSubheader);
        }
        else {
            System.out.println("We need to get information from this line");
        }
*/

        System.out.println("Triple Creation Complete\n");
    }

    /**
     * Checks if line starts with a title, header, or subheader tag.
     *  If so, sets the appropriate instance variables.
     */
    private boolean isTaggedLine(String line) {
        if(line.startsWith("<title>")){
            currentTitle = line.substring(7);
            currentHeader = null;
            currentSubheader = null;
            return true;
        }
        else if(line.startsWith("<header>")){
            currentHeader = line.substring(8);
            currentSubheader = null;
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
