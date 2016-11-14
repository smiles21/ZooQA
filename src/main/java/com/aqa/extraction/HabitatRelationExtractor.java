package com.aqa.extraction;

import com.aqa.kb.ConceptList;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitatRelationExtractor extends RelationExtractor {

    private final String[] HABITAT_PHRASES = {"lives in", "inhabits", "throughout", "found in", "occurs in", "occupy", "habitats"};

    private List<String> habitatWords;

    public HabitatRelationExtractor(ConceptList cl) {
        habitatWords = cl.getList();
        System.out.println("HabitatRelationExtractor created.");
    }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String s, Document currentDoc) {

        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>();

        // This needs to be changed somehow.  Perhaps just use the title of the article?
        String subject = currentDoc.getTitle();

        List<String> words   = sentence.words();
        List<String> lemmas  = sentence.lemmas();

        // Check if any of the key phrases exist in the sentence
        for(String phrase : HABITAT_PHRASES) {
            List<String> phraseAsList = Arrays.asList(phrase.split(" "));
            int indexOfPhrase = indexOfPhrase(words, phraseAsList);
            
            // We have a phrase in the sentence
            if(indexOfPhrase != -1) {
                
                boolean habitatInEightWords = false;
                // See if a habitat word appears after the phrase, but within 8 words
                for(int indexOfHabitatWord = indexOfPhrase;
                  indexOfHabitatWord < lemmas.size() && indexOfHabitatWord < indexOfPhrase+8;
                  indexOfHabitatWord++)
                {
                    if(habitatWords.contains(words.get(indexOfHabitatWord))){
                        habitatInEightWords = true;
                        break;
                    }
                }
                // The sentence has a habitat within 8 words of our phrase
                if(habitatInEightWords){
                    int currIndex = indexOfPhrase + phraseAsList.size();
                    while(habitatWords.contains(words.get(currIndex))
                            || words.get(currIndex).equalsIgnoreCase(",")
                            || words.get(currIndex).equalsIgnoreCase("and"))
                    {
                        ++currIndex;
                    }
                    // If the last word is a comma, "and", or "the, remove it.
                    while(words.get(currIndex-1).equalsIgnoreCase(",") 
                        || words.get(currIndex-1).equalsIgnoreCase("and")
                        || words.get(currIndex-1).equalsIgnoreCase("the"))
                        --currIndex;
                    
                    // Create the Triple and add it to the returned Triples
                    String value = createStringFromList(words.subList(indexOfPhrase, currIndex));
                    if(value.split(" ").length > 1 && !Arrays.asList(HABITAT_PHRASES).contains(value)){
                        Triple trip = new Triple(subject, "has-habitat", value, currentDoc, sentenceNumber);
                        triples.add(trip);
                    }
                }
            }
        }
        return triples;


    }


}
