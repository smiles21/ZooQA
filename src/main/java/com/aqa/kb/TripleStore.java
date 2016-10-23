package com.aqa.kb;

import com.aqa.kb.Document;
import com.aqa.kb.Triple;
import com.aqa.extraction.ExtractorCoordinator;

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
     * The ExtractorCoordinator to extract triples.
     */
    private ExtractorCoordinator extractors;

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
        this.extractors = new ExtractorCoordinator();
    }

    public void createTriples(int senctenceNumber, String sentence, Document currentDoc) {

        extractors.extractRelations(sentence);
    }

}
