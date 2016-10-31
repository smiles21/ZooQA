package com.aqa.extraction;

import com.aqa.ZooQA;
import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class WeightRelationExtractor implements RelationExtractor {

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
        String subject = "Nile Crocodile";

        List<String> words   = sentence.words();
        List<String> nerTags = sentence.nerTags();
        List<String> lemmas  = sentence.lemmas();
      
        for(String phrase : WEIGHT_PHRASES){
            int indexOfPhrase = indexOfPhrase(words, Arrays.asList(phrase.split(" ")));
            System.out.println(phrase + " " + indexOfPhrase);
            // We have a phrase in the sentence
            if(indexOfPhrase != -1){
                
                // See if a weight word appears after the phrase, but within 6 words
                for(int indexOfWeightWord = indexOfPhrase;
                  indexOfWeightWord < lemmas.size() && indexOfWeightWord < indexOfPhrase+6;
                  indexOfWeightWord++)
                {
                    System.out.println("lemma: " + lemmas.get(indexOfWeightWord));
                    // If we have a weight word, add the entire string to 
                    if(weightWords.contains(lemmas.get(indexOfWeightWord))){
                        String value = createStringFromList(words.subList(indexOfPhrase, indexOfWeightWord + 1));
                        Triple trip = new Triple(subject, "Weight", value, currentDoc, sentenceNumber);
                        triples.add(trip);
                    }
                }
            }

        }
        return triples;
    }

    // Take all strings in list and build them into one string
    private String createStringFromList(List<String> words){
        StringBuilder sb = new StringBuilder();
        for(String s : words)
            sb.append(s + " ");
        return sb.toString();
    }

    // Find the index of the phrase in words
    private int indexOfPhrase(List<String> words, List<String> phrase){
        int index = -1;
        int wordsSize = words.size();
        int phraseSize = phrase.size();

        for(int i = 0; i < wordsSize - phraseSize; i++){
            if(words.get(i).equals(phrase.get(0))){
                boolean flag = true;
                for(int j = 0; j < phraseSize; j++){
                    if(!words.get(j + i).equals(phrase.get(j))){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}
