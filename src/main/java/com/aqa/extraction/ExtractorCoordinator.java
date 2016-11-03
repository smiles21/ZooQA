package com.aqa.extraction;

import com.aqa.kb.ConceptList;
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

    /**
     * A semantic relation extractor for prey.
     */
    private PreyRelationExtractor preyExtractor;

    /**
     * A semantic relation extractor for location.
     */
    private LocationRelationExtractor locationExtractor;

    /**
     * A semantic relation extractor for habitat.
     */
    private HabitatRelationExtractor habitatExtractor;

    /**
     * A list of habitats..
     */
    private ConceptList habitatList;

    public ExtractorCoordinator() {
        weightExtractor = new WeightRelationExtractor();
        lengthExtractor = new LengthRelationExtractor();
        preyExtractor = new PreyRelationExtractor();
        locationExtractor = new LocationRelationExtractor();
        
        habitatList = new ConceptList("/habitats.txt");
        habitatExtractor = new HabitatRelationExtractor(habitatList);
        
    }

    public ArrayList<Triple> extractRelations(int sentenceNumber, String sentence, Document currentDoc){
        ArrayList<Triple> triples = new ArrayList<Triple>();

        ArrayList<Triple> extracted = weightExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(extracted.size() > 0)
            triples.addAll(extracted);

        extracted = lengthExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(extracted.size() > 0)
            triples.addAll(extracted);

        extracted = preyExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(extracted.size() > 0)
            triples.addAll(extracted);

        extracted = locationExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(extracted.size() > 0)
            triples.addAll(extracted);

        extracted = habitatExtractor.extractRelations(sentenceNumber, sentence, currentDoc);
        if(extracted.size() > 0)
            triples.addAll(extracted);
        return triples;
    }

}
