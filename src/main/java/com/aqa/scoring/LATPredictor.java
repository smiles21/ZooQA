package com.aqa.scoring;

import edu.stanford.nlp.simple.Sentence;

/**
 * A class to predict the lexical answer type of a query.
 *
 *  Modified version of code for WatsonJr by Tyler Yates.
 */
public class LATPredictor {

    public static LAT predictLAT(String q) {
        
        Sentence query = new Sentence(q);

        if(query.word(0).equalsIgnoreCase("where")) {
            return LAT.LOCATION;
        }
        for(final String lemma : query.lemmas()) {
            if(lemma.equalsIgnoreCase("habitat") || lemma.equalsIgnoreCase("ecosystem") || lemma.equalsIgnoreCase("environment") || lemma.equalsIgnoreCase("biome"))
                return LAT.HABITAT;
            if(lemma.equalsIgnoreCase("weigh") || lemma.equalsIgnoreCase("weight")
                || lemma.equalsIgnoreCase("heavy"))
                return LAT.WEIGHT;
            if(lemma.equalsIgnoreCase("length") || lemma.equalsIgnoreCase("long"))
                return LAT.LENGTH;
            if(lemma.equalsIgnoreCase("region") || lemma.equalsIgnoreCase("found")
                || lemma.equalsIgnoreCase("find") || lemma.equalsIgnoreCase("location")
                || lemma.equalsIgnoreCase("area"))
                return LAT.LOCATION;
        }
        return null;


    }

}
