package com.aqa.extraction;

import com.aqa.ZooQA;
import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class LengthRelationExtractor extends RelationExtractor {

        public List<String> lengthWords;

        public final String[] LENGTH_PHRASES = {"up to", "average", "between", "as much as", "averages", "measures", "measure", "length", "lengths"};

        public LengthRelationExtractor() {
            System.out.println("LengthRelationExtractor created.");
            String[] arr = {"meters", "meter", "foot", "feet"};
            lengthWords = Arrays.asList(arr);
            System.out.println("LengthWords: " + lengthWords);
        }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String s, Document currentDoc) {

        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>();

        // This needs to be changed somehow.  Perhaps just use the title of the article?
        String subject = currentDoc.getTitle();

        List<String> words   = sentence.words();
        List<String> lemmas  = sentence.lemmas();

        // Check if any of the key phrases exist in the sentence      
        for(String phrase : LENGTH_PHRASES){
            int indexOfPhrase = indexOfPhrase(words, Arrays.asList(phrase.split(" ")));
            // We have a phrase in the sentence
            if(indexOfPhrase != -1){
                
                // See if a length word appears after the phrase, but within 6 words
                for(int indexOfLengthWord = indexOfPhrase;
                  indexOfLengthWord < lemmas.size() && indexOfLengthWord < indexOfPhrase+8;
                  indexOfLengthWord++)
                {
                    // If we have a length word, add the entire string to the value of the Triple 
                    if(lengthWords.contains(lemmas.get(indexOfLengthWord))){
                        String value = createStringFromList(words.subList(indexOfPhrase, indexOfLengthWord + 1));
                        Triple trip = new Triple(subject, "has-length", value, currentDoc, sentenceNumber);
                        triples.add(trip);
                    }
                }
            }

        }
        return triples;
    }


}
