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
            if(lemma.equalsIgnoreCase("habitat") || lemma.equalsIgnoreCase("ecosystem") || lemma.equalsIgnoreCase("environment"))
                return LAT.HABITAT;
            if(lemma.equalsIgnoreCase("weight") || lemma.equalsIgnoreCase("weight")
                || lemma.equalsIgnoreCase("heavy"))
                return LAT.WEIGHT;
            if(lemma.equalsIgnoreCase("length") || lemma.equalsIgnoreCase("long"))
                return LAT.LENGTH;
        }
        return null;


    }

}
