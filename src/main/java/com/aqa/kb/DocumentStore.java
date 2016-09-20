package com.aqa.kb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DocumentStore {

    /*
     * Flags to print extra information and show statistics
     */
    private boolean explicit, stats;

    // Unfortunately had to hard code the file names
    private String[] filenames = {
        "/corpus/american-crocodile.txt", "/corpus/cuban-crocodile.txt",
        "/corpus/freshwater-crocodile.txt", "/corpus/morelets-crocodile.txt",
        "/corpus/mugger-crocodile.txt", "/corpus/new-guinea-crocodile.txt",
        "/corpus/nile-crocodile.txt", "/corpus/orinoco-crocodile.txt",
        "/corpus/phillipine-crocodile.txt", "/corpus/saltwater-crocodile.txt",
        "/corpus/siamese-crocodile.txt", "/corpus/west-african-crocodile.txt"
    };
    
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
                
                while(scan.hasNextLine()) {
                    System.out.println(scan.nextLine());
                }

            }
        } catch(Exception fofe) {
            fofe.printStackTrace();
        }
        System.out.println();


    }

}
