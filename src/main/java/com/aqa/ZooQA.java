package com.aqa;

import com.aqa.kb.KnowledgeBase;
import com.aqa.kb.Triple;
import com.aqa.scoring.LAT;
import com.aqa.scoring.LATPredictor;
import com.aqa.scoring.LuceneScorer;

import java.io.InputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * ZooQA ("Zoo-Kah")
 */
public class ZooQA {
 
    /**
     * The number of sentences to show to the user
     */
    private final int NUM_RESULTS_TO_PRINT = 5;

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
     * The DocumentStore for all the documents in the corpus
     */
    private KnowledgeBase knowledgeBase;

    /**
     * The LuceneScorer used for scoring documents
     */
    private LuceneScorer luceneScorer;

    public ZooQA(boolean explicit, boolean stats, boolean experiment) {
        this.explicit = explicit;
        this.stats = stats;
        this.experiment = experiment;

        if(experiment)
            printExperimentIntro();
        else 
            printIntro();
        
        ZooQA.pressEnterToContinue();

        if(explicit && !experiment)
            System.out.printf("[EXPLICIT] Flags: {explicit: %b, stats: %b, experiment: %b}\n\n", this.explicit, this.stats, this.experiment);

        // Create the knowledge base by reading the documents
        knowledgeBase = new KnowledgeBase(this.explicit, this.stats, this.experiment);
        knowledgeBase.createCorpus();
       
        luceneScorer = new LuceneScorer(knowledgeBase);
        
    }

    private void printExperimentIntro() { 
        System.out.println("\n************************************************************");

        System.out.println("Welcome to the Zoo-QA System Experiment Mode.\n");
        System.out.println("In this mode, you will be given a series of question");
        System.out.println("and answer pairs.  Your job will be to rank both the");
        System.out.println("answers in terms of relevance and the relavance itself.");

        System.out.println("\nMay need to add more here by how to score the confidence?");

        System.out.println("\n************************************************************");
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

    private void printSortedResults(HashMap<String, Float> scores) {
        if(scores == null || scores.size() == 0) {
            System.out.println("No scores to output.");
            return;
        }
        // Keep track of the sentences we have output
        HashSet<String> printed = new HashSet<String>(); 

        // Print out column headers
        System.out.println("\n Rank |  Score | Sentence");
        System.out.println("-------------------------");

        // Find the top scores and print them
        for(int i = 0; i < NUM_RESULTS_TO_PRINT; i++) {
            String topScoredSentence = null;
            float topScore = (float)-1.0;
            for(String sentence : scores.keySet()) {
                if(!printed.contains(sentence)) {
                    float currScore = scores.get(sentence);
                    if(currScore > topScore) {
                        topScoredSentence = sentence;
                        topScore = currScore;
                    } 
                }
            }
            // Print out the formatted result
            if(topScore >= 0.5)
                System.out.printf("%5d | %2.4f | %s\n", i+1, topScore, topScoredSentence);

            // Store this sentence as printed
            printed.add(topScoredSentence);

        }

    }

    public void runExperiments() {

        try {
            InputStream in = getClass().getResourceAsStream("/questions/questions.txt");
            Scanner fileScan = new Scanner(in);

            PrintWriter pw = new PrintWriter("scores.txt", "UTF-8");

            String question = null;
            while(fileScan.hasNextLine()) {
                question = fileScan.nextLine();
                pw.print(question + "\n");
            
                HashMap<String, Float> finalScores = queryKnowledgeBase(question);
                if(finalScores != null) {
                    for(String ans : finalScores.keySet()) {

                        // Show 
                        printQuestionAnswerPair(question, ans, finalScores.get(question));

                        System.out.print("Answer Rating: ");
                        int answerScore = returnIntInput();


                    }

                } else {
                    pw.print(question);
                    pw.print("No Answers\n");
                }

            }

            pw.close();
            fileScan.close();

        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    private int returnIntInput() {
        
        Scanner scan = new Scanner(System.in);
        int retVal = -1;

        do {
            
            try {
                String s = scan.nextLine();
                int temp = Integer.parseInt(s);
                retVal = temp;
            } catch(Exception e) {
                System.out.println("Could not parse the integer value");
            }

            if(retVal < 0 || retVal > 5) {
                System.out.println("Make sure you enter between 0-5");
            }

        } while (retVal < 0 || retVal > 5);

        return retVal;
    }

    private void printQuestionAnswerPair(String question, String answer, float score) {

        System.out.println("\n*********************************************\n");
        System.out.println("Question: " + question);
        
        System.out.println("\nAnswer  : " + answer);
        System.out.println("Score     : " + score + "\n");
    }

    private HashMap<String, Float> queryKnowledgeBase(String query) {

        if(query.trim().equalsIgnoreCase(""))
            return null;

        LAT lat = LATPredictor.predictLAT(query);

        String subject = null;
        for(String s : this.knowledgeBase.getSubjects()){
            if(query.toUpperCase().contains(s.toUpperCase())){
                subject = s;
                break;
            }
        }

        HashMap<String, Float> tripleResults = null;
        HashMap<String, Float> luceneResults = null;

        // Put in code to grab all Triples from the KB with the 
        //  subject and relation in it.
        if(subject != null && lat != null) {
            // Get the result sentences from Lucene
            luceneResults = luceneScorer.scoreSentences(knowledgeBase, query, subject);
            // Get the result sentences from the KnowledgeBase
            tripleResults = this.knowledgeBase.scoreSentences(subject, lat.relation());
        } else {
            System.out.println("Couldn't isolate a subject or relation.");
        }


        // Compute the final results
        HashMap<String, Float> finalScores = computeFinalScores(tripleResults, luceneResults);
       
        return finalScores;
 
    }

    private HashMap<String, Float> computeFinalScores(HashMap<String, Float> scoresA, HashMap<String, Float> scoresB) {

        // Give scoresA and scoresB a default value if we dont' have one
        if(scoresA == null)
            scoresA = new HashMap<String, Float>();
        if(scoresB == null)
            scoresB = new HashMap<String, Float>();

        HashMap<String, Float> results = new HashMap<String, Float>();
    
        // Weight the first scores by one-half and add it to results
        for(String sentence : scoresA.keySet()) 
            results.put(sentence, (float)0.5 * scoresA.get(sentence));

        // Add the other half of the scores to the final results
        for(String sentence : scoresB.keySet()) {
            if(results.containsKey(sentence))
                results.put(sentence, results.get(sentence) + (float)0.5 * scoresB.get(sentence));
            else
                results.put(sentence, (float)0.5 * scoresB.get(sentence));
        }

        return results;
    }

    public void promptQuery() {

        Scanner scan = new Scanner(System.in);

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

            HashMap<String, Float> tripleResults = null;
            HashMap<String, Float> luceneResults = null;

            if(explicit && !experiment)
                System.out.printf("[EXPLICIT] LAT: %s; SUBJECT: %s\n", lat, subject);

            // Put in code to grab all Triples from the KB with the 
            //  subject and relation in it.
            if(subject != null && lat != null) {
                // Get the result sentences from Lucene
                luceneResults = luceneScorer.scoreSentences(knowledgeBase, query, subject);
                // Get the result sentences from the KnowledgeBase
                tripleResults = this.knowledgeBase.scoreSentences(subject, lat.relation());
            } else {
                System.out.println("Couldn't isolate a subject or relation.");
            }
    

            // Compute the final results
            HashMap<String, Float> finalScores = computeFinalScores(tripleResults, luceneResults);
          
            // Show the results to the user 
            printSortedResults(finalScores); 

        } while(true);

    }

    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        try {
            System.in.read();
            System.out.println();
        }
        catch(Exception e) {}
    }

    public static void main(String[] args) {
        boolean stats = false;
        boolean explicit = false;
        boolean experiment = false;

        for(String arg : args) {
            if(arg.equals("--stats"))
                stats = true;
            else if(arg.equals("--explicit"))
                explicit = true;
            else if(arg.equals("--experiment"))
                experiment = true;
            else 
                throw new IllegalArgumentException("Argument not recognized: " + arg);
        }

        ZooQA zooQA = new ZooQA(explicit, stats, experiment);
        if(experiment) {
            zooQA.runExperiments();
        } else {
            zooQA.promptQuery();
        }
    }
}
