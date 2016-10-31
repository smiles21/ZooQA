package com.aqa.extraction;

import com.aqa.extraction.WeightRelationExtractor;
import com.aqa.kb.Document;

public class ExtractorCoordinator {

    /**
     * A semantic relation extractor for weight.
     */
    private WeightRelationExtractor weightExtractor;

    public ExtractorCoordinator() {
        weightExtractor = new WeightRelationExtractor();
    }

    public void extractRelations(int sentenceNumber, String sentence, Document currentDoc){
        weightExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
    }

}
