package com.aqa.extraction;

import com.aqa.ZooQA;
import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationRelationExtractor extends RelationExtractor {

    public final String[] LOCATION_PHRASES = {"lives in", "inhabits", "throughout", "found in", "occurs in"};

    public LocationRelationExtractor() {
        System.out.println("LocationRelationExtractor created.");
    }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String s, Document currentDoc) {

        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>();

        // This needs to be changed somehow.  Perhaps just use the title of the article?
        String subject = currentDoc.getTitle();

        List<String> words   = sentence.words();
        List<String> lemmas  = sentence.lemmas();
        List<String> nerTags = sentence.nerTags();

        // Check if any of the key phrases exist in the sentence
        for(String phrase : LOCATION_PHRASES) {
            List<String> phraseAsList = Arrays.asList(phrase.split(" "));
            int indexOfPhrase = indexOfPhrase(words, phraseAsList);
            
            // We have a phrase in the sentence
            if(indexOfPhrase != -1) {
                
                boolean locationInEightWords = false;
                // See if a location word appears after the phrase, but within 8 words
                for(int indexOfLocationWord = indexOfPhrase;
                  indexOfLocationWord < lemmas.size() && indexOfLocationWord < indexOfPhrase+8;
                  indexOfLocationWord++)
                {
                    if(nerTags.get(indexOfLocationWord).equalsIgnoreCase("LOCATION")){
                        locationInEightWords = true;
                        break;
                    }
                }
                // The NER has found a LOCATION within 8 words of our phrase
                if(locationInEightWords){
                    int currIndex = indexOfPhrase + phraseAsList.size();
                    while(nerTags.get(currIndex).equalsIgnoreCase("LOCATION")
                            || words.get(currIndex).equalsIgnoreCase("the")
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
                    if(value.split(" ").length > 1 && !Arrays.asList(LOCATION_PHRASES).contains(value)){
                        Triple trip = new Triple(subject, "has-location", value, currentDoc, sentenceNumber);
                        triples.add(trip);
                    }
                }
            }
        }
        return triples;
    }


}
