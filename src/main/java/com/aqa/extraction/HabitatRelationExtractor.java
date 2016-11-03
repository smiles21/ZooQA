package com.aqa.extraction;

import com.aqa.kb.ConceptList;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitatRelationExtractor extends RelationExtractor {

    private final String[] HABITAT_PHRASES = {"lives in", "inhabits", "throughout", "found in", "occurs in", "occupy"};

    private List<String> habitatWords;

    public HabitatRelationExtractor(ConceptList cl) {
        System.out.println("HabitatRelationExtractor created.");
        habitatWords = cl.getList();
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
                
                boolean habitatInSixWords = false;
                System.out.println(phrase);
                // See if a habitat word appears after the phrase, but within 6 words
                for(int indexOfHabitatWord = indexOfPhrase;
                  indexOfHabitatWord < lemmas.size() && indexOfHabitatWord < indexOfPhrase+6;
                  indexOfHabitatWord++)
                {
                    System.out.println(words.get(indexOfHabitatWord));
                    if(habitatWords.contains(words.get(indexOfHabitatWord))){
                        habitatInSixWords = true;
                        break;
                    }
                }
                // The sentence has a habitat within 6 words of our phrase
                if(habitatInSixWords){
                    int currIndex = indexOfPhrase + phraseAsList.size();
                    while(habitatWords.contains(words.get(currIndex))
                            || words.get(currIndex).equals(",")
                            || words.get(currIndex).equals("and"))
                    {
                        System.out.println(words.get(currIndex));
                        ++currIndex;
                    }
                    // If the last "word" in the list is a comma or 'and', remove it.
                    if(words.get(currIndex-1).equals(",") || words.get(currIndex-1).equals("and"))
                        --currIndex;
                    
                    // Create the Triple and add it to the returned Triples
                    String value = createStringFromList(words.subList(indexOfPhrase, currIndex));
                    Triple trip = new Triple(subject, "has-habitat", value, currentDoc, sentenceNumber);
                    triples.add(trip);
                }
            }
        }
        return triples;


    }


}
