package com.aqa;

import com.aqa.kb.KnowledgeBase;

import java.util.Scanner;

/**
 * ZooQA ("Zoo-Kah")
 *
 */
public class ZooQA {

    /**
     * Flag for displaying more than the standard information
     */
    private boolean explicit;

    /**
     * Flag for displaying statistics and counts throughout the pipeline
     */
    private boolean stats;

    /**
     * The DocumentStore for all the documents in the corpus
     */
    private KnowledgeBase knowledgeBase;

    public ZooQA(boolean explicit, boolean stats) {
        this.explicit = explicit;
        this.stats = stats;        

        printIntro();
        pressEnterToContinue();

        if(explicit)
            System.out.printf("[EXPLICIT] Flags: {explicit: %b, stats: %b}\n\n", this.explicit, this.stats);

        // Need to add corpus creation here
        knowledgeBase = new KnowledgeBase(this.explicit, this.stats);
        knowledgeBase.createCorpus();


    }


    private void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        try {
            System.in.read();
            System.out.println();
        }
        catch(Exception e) {}
    }

    private void printIntro() {
        System.out.println("\n************************************************************");
        
        System.out.println("Welcome to the Zoo-QA System.\n");
        System.out.println("ZooQA is designed to answer questions on animals. ");
        System.out.println("The default corpus of documents is about crocodiles,");
        System.out.println("namely those in the genus Crocodylus.");

        System.out.println("\nSimply enter your question into the terminal when");
        System.out.println("prompted, or enter an empty query into the prompt to");
        System.out.println("exit the system.");

        System.out.println("************************************************************");
    }

    public void promptQuery() {

        Scanner scan = new Scanner(System.in);
        
        do {
            
            System.out.print("\nQuery: ");
            String query = scan.nextLine();

            if(query.trim().equalsIgnoreCase(""))
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
