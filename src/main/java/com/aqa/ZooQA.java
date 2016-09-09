package com.aqa;

import java.util.Scanner;

/**
 * ZooQA ("Zoo-Kah")
 *
 */
public class ZooQA 
{

    public ZooQA(boolean stats, boolean explicit) {
        
        printIntro();

        if(explicit)
            System.out.printf("Flags: {explicit: %b, stats: %b}\n", explicit, stats);

        // Need to add corpus creation here

        promptQuery();

    }

    public void printIntro()
    {
        System.out.println("\n************************************************************");
        
        System.out.println("Welcome to the Zoo-QA System.\n");
        System.out.println("ZooQA is designed to answer questions on animals. ");
        System.out.println("The default corpus of documents is about crocodiles,");
        System.out.println("namely those in the genus Crocodylus.");

        System.out.println("\nSimply enter your question into the terminal when");
        System.out.println("prompted, or enter 'quit' into the prompt to exit");
        System.out.println("the system.");

        System.out.println("************************************************************\n");
    }

    public void promptQuery() {

        Scanner scan = new Scanner(System.in);
        
        do {
            
            System.out.print("\nQuery: ");
            String query = scan.nextLine();

            if(query.equalsIgnoreCase("quit"))
                break;

            // Need to add code to parse the query, then pose that question to the knowledge base
            System.out.printf("You entered: '%s'\n", query);

        } while(true);

    }

    public static void main(String[] args)
    {
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



        ZooQA zooQA = new ZooQA(stats, explicit);
    }
}
