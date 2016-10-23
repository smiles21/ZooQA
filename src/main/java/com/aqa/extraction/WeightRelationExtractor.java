package com.aqa.extraction;

import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.List;

public class WeightRelationExtractor implements RelationExtractor {

    public static final LENGTH_WORDS = {"meter", "centimeter", "foot", "inch"};

    public WeightRelationExtractor() {
        System.out.println("WeightRelationExtractor created.");
    }

    public ArrayList<Triple> extractRelations(String s) {
        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>(); 

        List<String> words   = sentence.words();
        List<String> nerTags = sentence.nerTags();
        List<String> lemmas  = sentence.lemmas();

        System.out.println("sentence: " + words);

        System.out.println("lemmas: " + lemmas);

        System.out.println("nerTags : " + nerTags + "\n");        

        for(int i = 0; i < words.size(); i++) {
            if(LENGTH_WORDS.contains(lemmas[i])) {
                int lengthIndex = i - 1;
                while(lengthIndex > 0 && 
                      (nerTags[lengthIndex].equals("NUMBER")
                        || words[lengthIndex].equals("to"))){

                    // WE NEED TO ADD CODE HERE TO ADD THIS TO A STRING, THEN
                    //  CREATE THE TRIPLE AND ADD IT TO THE TRIPLES ARRAYLIST
                    //  TO RETURN.
                }

            }
            
        }

        return triples;
    }
}
