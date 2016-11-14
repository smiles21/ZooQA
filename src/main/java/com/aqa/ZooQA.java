package com.aqa;

import com.aqa.kb.KnowledgeBase;
import com.aqa.kb.Triple;
import com.aqa.scoring.LAT;
import com.aqa.scoring.LATPredictor;
import com.aqa.scoring.TripleScorer;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ZooQA ("Zoo-Kah")
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
        ZooQA.pressEnterToContinue();

        if(explicit)
            System.out.printf("[EXPLICIT] Flags: {explicit: %b, stats: %b}\n\n", this.explicit, this.stats);

        knowledgeBase = new KnowledgeBase(this.explicit, this.stats);
        knowledgeBase.createCorpus();


    }

    public static void pressEnterToContinue() {
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
        TripleScorer tripleScorer = new TripleScorer();        

        do {
            
            System.out.print("\nQuery: ");
            String query = scan.nextLine();

            if(query.trim().equalsIgnoreCase(""))
                break;

            LAT lat = LATPredictor.predictLAT(query);

            String subject = null;
            for(String s : this.knowledgeBase.getSubjects()){
                if(query.toUpperCase().contains(s.toUpperCase())){
                    subject = s;
                    break;
                }
            }

            // Put in code to grab all Triples from the KB with the 
            //  subject and relation in it.

            ArrayList<Triple> tripleResults = this.knowledgeBase.getResultTriples(subject, lat.relation());

            if(tripleResults.size() > 0){
                for(Triple t : tripleResults)
                    System.out.println(t);
            } else {
                System.out.println("No triples about that.");
            }

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
