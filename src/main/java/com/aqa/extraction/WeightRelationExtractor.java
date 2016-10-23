package com.aqa.extraction;

import com.aqa.extraction.RelationExtractor;
import com.aqa.kb.Triple;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;

public class WeightRelationExtractor implements RelationExtractor {

    public WeightRelationExtractor() {
        System.out.println("Weight relation extractor");
    }

    public ArrayList<Triple> extractRelations(String s) {
        Sentence sentence = new Sentence(s);
        ArrayList<Triple> triples = new ArrayList<Triple>(); 

        System.out.println("Made it to here!");

        System.out.println("sentence: " + sentence.words());

        System.out.println("Went past the words");

        System.out.println("nerTags : " + sentence.nerTags());        

        return triples;
    }

}
