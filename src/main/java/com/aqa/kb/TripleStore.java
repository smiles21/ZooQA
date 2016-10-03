package com.aqa.kb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;

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

    /**
     * The store of all the triples we are storing.
     *   The primary key is the for all information regarding the key.  
     */
    private Map<String, Collection<Triple>> tripleMap;

    public TripleStore(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;

        this.tripleMap = new HashMap<String, <Collection<Triple>>();
    }

    public void createTriples() {
        System.out.print("Creating Triples... ");


        

        // If the line has title, header, or subheader info, don't index it.
        //  Otherwise, store information from the sentence.
        if(isTaggedLine(currentLine)) {
            if(explicit)
                System.out.printf("[EXPLICIT] Title: %s, Header: %s, Subheader: %s\n", currentTitle, currentHeader, currentSubheader);
        }
        else {
            System.out.println("We need to get information from this line");
        }


        System.out.println("Triple Creation Complete\n");
    


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
