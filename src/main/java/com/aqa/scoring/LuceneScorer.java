package com.aqa.scoring;

import com.aqa.kb.KnowledgeBase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LuceneScorer {
    private static final String ID_FIELD = "id";
    private static final String TEXT_FIELD = "text";
    private static final int MAX_RESULTS = 10;

    /**
     * The in-memory Directory where we are storing the index
     */
    private final Directory index = new RAMDirectory();

    /**
     * A Map from the index sentence number to the sentence itself
     */

    public LuceneScorer(KnowledgeBase knowledgeBase) {
        
        try {
            // Build an IndexWriter
            final Analyzer analyzer = new StandardAnalyzer();
            final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            final IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig);

            // Add each sentence from each document in the KnowledgeBase to the Index
            ArrayList<com.aqa.kb.Document> documents = knowledgeBase.getDocuments();
            for(int i = 0; i < documents.size(); i++){

                // Get the document and sentences of that document
                com.aqa.kb.Document kbDoc = documents.get(i);
                ArrayList<String> sentences = kbDoc.getSentences();

                // Add each sentence to the index.  It is numbered as 
                //  <DocumentNumber>:<SentenceNumber> for look-up during scoring
                for(int j = 0; j < sentences.size(); j++) {
                    final Document luceneDoc = new Document();
                    final String id = i + ":" + j;
                    luceneDoc.add(new StringField(ID_FIELD, id, Field.Store.YES));
                    luceneDoc.add(new TextField(TEXT_FIELD, sentences.get(i), Field.Store.YES));
                    indexWriter.addDocument(luceneDoc);
                }
            }
            indexWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("LuceneScorer Created");
    }

    public HashMap<String, Float> scoreSentences(KnowledgeBase knowledgeBase, String question) {

        HashMap<String, Float> results = new HashMap<String, Float>();
        
        try {
            // Open the index for searching
            final IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(index));
            final Analyzer analyzer = new StandardAnalyzer();

            // Create a query and a parser for the query
            final QueryParser parser = new QueryParser(TEXT_FIELD, analyzer);
            final Query query = parser.parse(question);

            // Get the top ranked answers from Lucene
            final TopDocs topDocs = indexSearcher.search(query, MAX_RESULTS);
            final ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for(ScoreDoc scoreDoc : scoreDocs) {
                String id = indexSearcher.getIndexReader().document(scoreDoc.doc).getField(ID_FIELD).stringValue();

                // Split the doc on the colon, having structure 
                //  [documentIndex, sentenceIndex]
                String[] stringCoordinates = id.split(":");
                int docIndex = Integer.parseInt(stringCoordinates[0]);
                int sentenceIndex = Integer.parseInt(stringCoordinates[1]);
                String sentence = knowledgeBase.getDocumentByIndex(docIndex).getSentenceByIndex(sentenceIndex);
                results.put(sentence, scoreDoc.score);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;


    }

}
