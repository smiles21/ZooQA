package com.aqa.kb;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ConceptList {

    /**
     * The filename for the resource of this list.
     */
    private String filename;

    /**
     * Makes sure the list cannot be made twice
     */
    private boolean isCreated;
    
    /**
     * The list of concepts...
     */
    private List<String> concepts;

    public ConceptList(String fn) {
        filename = fn;
        isCreated = false;
        concepts = new LinkedList<String>();
    }

    public List<String> getList() {
        if(isCreated)
            return concepts;
        return populateList();

    }

    private List<String> populateList() {

        try {
            InputStream in = getClass().getResourceAsStream(filename);
            Scanner scan = new Scanner(in);

            String currentLine = null;
            while(scan.hasNextLine()) {
                // Remove any newline characters at the end of the line
                currentLine = scan.nextLine().replaceAll("\n", "");
                concepts.add(currentLine);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        return concepts;
    }

}



