package com.aqa.extraction;

import com.aqa.extraction.WeightRelationExtractor;


public class ExtractorCoordinator {

    /**
     * A semantic relation extractor for weight.
     */
    private WeightRelationExtractor weightExtractor;

    public ExtractorCoordinator() {
        weightExtractor = new WeightRelationExtractor();
    }

    public void extractRelations(String sentence){
        weightExtractor.extractRelations(sentence);
    }

}
