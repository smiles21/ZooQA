package com.aqa.extraction;

import com.aqa.kb.ConceptList;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitatRelationExtractor extends RelationExtractor {

    private final String[] HABITAT_PHRASES = {"lives in", "inhabits", "throughout", "found in", "occurs in"};

    private List<String> habitatWords;

    public HabitatRelationExtractor(ConceptList cl) {
        System.out.println("HabitatRelationExtractor created.");
        habitatWords = cl.getList();
        System.out.println(habitatWords);
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
