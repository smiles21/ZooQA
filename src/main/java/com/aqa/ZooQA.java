package com.aqa;

import com.aqa.kb.DocumentStore;

import java.util.Scanner;

/**
 * ZooQA ("Zoo-Kah")
 *
 */
public class ZooQA {

    /*
     * Flag for displaying more than the standard information
     */
    private boolean explicit;

    /*
     * Flag for displaying statistics and counts throughout the pipeline
     */
    private boolean stats;

    /*
     * The DocumentStore for all the documents in the corpus
     */
    private DocumentStore docStore;

    public ZooQA(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;        


        printIntro();

        if(explicit)
            System.out.printf("[DEBUG] Flags: {explicit: %b, stats: %b}\n\n", this.explicit, this.stats);

        // Need to add corpus creation here
        docStore = new DocumentStore(this.explicit, this.stats);
        createCorpus();


    }

    private void createCorpus() {
        System.out.println("Creating Corpus...");

        docStore.loadDocuments();

        System.out.println("Corpus Creation Successful");
    }

    private void printIntro() {
        System.out.println("\n************************************************************");
        
        System.out.println("Welcome to the Zoo-QA System.\n");
        System.out.println("ZooQA is designed to answer questions on animals. ");
        System.out.println("The default corpus of documents is about crocodiles,");
        System.out.println("namely those in the genus Crocodylus.");

        System.out.println("\nSimply enter your question into the terminal when");
        System.out.println("prompted, or enter 'exit' into the prompt to exit");
        System.out.println("the system.");

        System.out.println("************************************************************");
    }

    public void promptQuery() {

        Scanner scan = new Scanner(System.in);
        
        do {
            
            System.out.print("\nQuery: ");
            String query = scan.nextLine();

            if(query.equalsIgnoreCase("exit"))
                break;

            // Need to add code to parse the query, then pose that question to the knowledge base
            System.out.printf("You entered: '%s'\n", query);

        } while(true);

    }

    public static void main(String[] args) {
        boolean stats = false;
        boolean explicit = false;

        for(String arg : args) {
            if(arg.equals("-stats"))
                stats = true;
            else if(arg.equals("-explicit"))
                explicit = true;
            else 
                throw new IllegalArgumentException("Argument not recognized: " + arg);
        }

        ZooQA zooQA = new ZooQA(explicit, stats);
        zooQA.promptQuery();
    }
}
