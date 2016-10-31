package com.aqa.extraction;

import com.aqa.extraction.WeightRelationExtractor;
import com.aqa.kb.Document;
import com.aqa.kb.Triple;

import java.util.ArrayList;

public class ExtractorCoordinator {

    /**
     * A semantic relation extractor for weight.
     */
    private WeightRelationExtractor weightExtractor;

    /**
     * A semantic relation extractor for length.
     */
    private LengthRelationExtractor lengthExtractor;

    public ExtractorCoordinator() {
        weightExtractor = new WeightRelationExtractor();
        lengthExtractor = new LengthRelationExtractor();
    }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String sentence, Document currentDoc){
        ArrayList<Triple> triples = new ArrayList<Triple>();

        ArrayList<Triple> weightRelations = weightExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(weightRelations.size() > 0)
            triples.addAll(weightRelations);

        ArrayList<Triple> lengthRelations = lengthExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(lengthRelations.size() > 0)
            triples.addAll(lengthRelations);

        return triples;
    }

}
