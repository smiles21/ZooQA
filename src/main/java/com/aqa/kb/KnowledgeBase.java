package com.aqa.kb;

import com.aqa.kb.Document;
import com.aqa.kb.DocumentStore;
import com.aqa.kb.TripleStore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

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
     * Flag for whether we are entering experiment mode
     */
    private boolean experiment;

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
     * The DocumentStore where the corpus documents are kept.
     */
    private DocumentStore docStore;

    /**
     * The TripleStore where the triples extracted from the corpus are stored.
     */
    private TripleStore tripleStore;

    /**
     * The subjects that the TripleStore has the subjects.
     */
    private HashSet<String> subjects;


    // Unfortunately had to hard code the file names
    private String[] filenames = {
        "/corpus/american-crocodile.txt", "/corpus/cuban-crocodile.txt",
        "/corpus/freshwater-crocodile.txt", "/corpus/morelets-crocodile.txt",
        "/corpus/mugger-crocodile.txt", "/corpus/new-guinea-crocodile.txt",
        "/corpus/nile-crocodile.txt", "/corpus/orinoco-crocodile.txt",
        "/corpus/philippine-crocodile.txt", "/corpus/saltwater-crocodile.txt",
        "/corpus/siamese-crocodile.txt", "/corpus/west-african-crocodile.txt"
    };


    // This is just to test the file-reading and knowledge base construction capabilities on only one file.
//    private String[] filenames = {"/corpus/nile-crocodile.txt"};

    public KnowledgeBase(boolean explicit, boolean stats, boolean experiment) {
        this.explicit = explicit;
        this.stats = stats;
        this.experiment = experiment;

        docStore = new DocumentStore(this.explicit, this.stats);
        tripleStore = new TripleStore(this.explicit, this.stats);
        subjects = new HashSet<String>();
    }

    public void createCorpus() { 
        System.out.println("Creating Corpus...");
                
        try {
            for(String filename : filenames) {
                InputStream in = getClass().getResourceAsStream(filename);
                Scanner scan = new Scanner(in);

                Document currentDoc = new Document();
                this.docStore.addDocument(currentDoc);
                String currentLine = null;
                int sentenceNumber = 0;

                if(explicit && !experiment)
                    System.out.println("[EXPLICIT] Extracting Triples from " + filename);
 
                while(scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    if(isTitleLine(currentLine))
                        currentDoc.setTitle(currentLine.substring(7));
                    else if(!isTitleLine(currentLine)
                        && !isHeaderLine(currentLine)
                        && !isSubheaderLine(currentLine)) {
                        
                        this.tripleStore.createTriples(sentenceNumber, currentLine, currentDoc);
                        currentDoc.addSentence(sentenceNumber, currentLine);
                        ++sentenceNumber;
                    }
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(explicit && !experiment)
            this.tripleStore.printStore();

        for(String subject : this.tripleStore.getStore().keySet()){
            if(!subjects.contains(subject))
                subjects.add(subject.trim());
        }

        System.out.println("Corpus Creation Successful");

    }

    public HashSet<String> getSubjects() {
        return subjects;
    }

    public Document getDocumentByIndex(int index) {
        return docStore.getDocumentByIndex(index);
    }

    public ArrayList<Document> getDocuments() {
        return docStore.getDocuments();
    }

    public HashMap<String, Float> scoreSentences(String subject, String relation) {
        HashMap<String, Float> results = new HashMap<String, Float>();

        for(Triple t : this.tripleStore.getTriplesOfSubject(subject)){
            if(t.hasRelation(relation))
                results.put(t.getDocument().getSentenceByIndex(t.getSentenceNumber()), (float)1.0);
        }
        
        return results;
    }

    /**
     * Checks if line starts with a title tag.
     *  If so, sets the appropriate instance variables.
     */
    private boolean isTitleLine(String line){
        if(line.startsWith("<title>")){
            currentTitle = line.substring(7);
            currentHeader = null;
            currentSubheader = null;
            return true;
        }
        return false;
    }

    /**
     * Checks if line starts with a header tag.
     *  If so, sets the appropriate instance variables.
     */
    private boolean isHeaderLine(String line) {
        if(line.startsWith("<header>")){
            currentHeader = line.substring(8);
            currentSubheader = null;
            return true;
        }
        return false;
    }

    /**
     * Checks if line starts with a subheader tag.
     *  If so, sets the appropriate instance variables.
     */
    private boolean isSubheaderLine(String line){
        if(line.startsWith("<subheader>")){
            currentSubheader = line.substring(11);
            return true;
        }
        return false;
    }


}
