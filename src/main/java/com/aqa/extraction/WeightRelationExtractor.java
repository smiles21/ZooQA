package com.aqa.extraction;

import com.aqa.ZooQA;
import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class WeightRelationExtractor extends RelationExtractor {

        public List<String> weightWords;

        public final String[] WEIGHT_PHRASES = {"up to", "average", "between", "as much as", "averages", "weighs", "weigh"};

        public WeightRelationExtractor() {
            System.out.println("WeightRelationExtractor created.");
            String[] arr = {"pound", "pounds", "kilograms", "kilogram", "ton", "tons", "tonnes"};
            weightWords = Arrays.asList(arr);
            System.out.println("WeightWords: " + weightWords);
        }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String s, Document currentDoc) {

        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>();

        // This needs to be changed somehow.  Perhaps just use the title of the article?
        String subject = currentDoc.getTitle();

        List<String> words   = sentence.words();
        List<String> lemmas  = sentence.lemmas();

        // Check if any of the key phrases are present in the sentence      
        for(String phrase : WEIGHT_PHRASES){
            int indexOfPhrase = indexOfPhrase(words, Arrays.asList(phrase.split(" ")));
            // We have a phrase in the sentence
            if(indexOfPhrase != -1){
                
                // See if a weight word appears after the phrase, but within 6 words
                for(int indexOfWeightWord = indexOfPhrase;
                  indexOfWeightWord < lemmas.size() && indexOfWeightWord < indexOfPhrase+6;
                  indexOfWeightWord++)
                {
                    // If we have a weight word, add the entire string to value of the Triple
                    if(weightWords.contains(lemmas.get(indexOfWeightWord))){
                        String value = createStringFromList(words.subList(indexOfPhrase, indexOfWeightWord + 1));
                        Triple trip = new Triple(subject, "has-weight", value, currentDoc, sentenceNumber);
                        triples.add(trip);
                    }
                }
            }

        }
        return triples;
    }


}
