package com.aqa.kb;

import com.aqa.kb.DocumentStore;
import com.aqa.kb.TripleStore;

public class KnowledgeBase {

    /**
     * Flag for displaying more than the standard information
     */
    private boolean explicit;

    /**
     * Flag for displaying statistics and counts throughout the pipeline
     */
    private boolean stats;

    /**
     * The DocumentStore where the corpus documents are kept.
     */
    private DocumentStore docStore;

    /**
     * The TripleStore where the triples extracted from the corpus are stored.
     */
    private TripleStore tripleStore;

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

    public KnowledgeBase(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;

        docStore = new DocumentStore(this.explicit, this.stats);
        tripleStore = new TripleStore(this.explicit, this.stats);
    }

    public void createCorpus() { 
        System.out.println("Creating Corpus...");
        docStore.loadDocuments(filenames);
        tripleStore.createTriples();
        System.out.println("Corpus Creation Successful");
    }


}
