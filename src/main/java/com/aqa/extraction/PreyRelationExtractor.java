package com.aqa.extraction;

import com.aqa.ZooQA;
import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class PreyRelationExtractor extends RelationExtractor {

        public List<String> preyWords;

        public final String[] PREY_PHRASES = {"eats", "eat"};

        public PreyRelationExtractor() {
            System.out.println("PreyRelationExtractor created.");
            String[] arr = {"pound", "pounds", "kilograms", "kilogram", "ton", "tons", "tonnes"};
            preyWords = Arrays.asList(arr);
            System.out.println("PreyWords: " + preyWords);
        }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String s, Document currentDoc) {

        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>();

        // This needs to be changed somehow.  Perhaps just use the title of the article?
        String subject = currentDoc.getTitle();

        List<String> words   = sentence.words();
        List<String> lemmas  = sentence.lemmas();

        return triples;
    }


}

