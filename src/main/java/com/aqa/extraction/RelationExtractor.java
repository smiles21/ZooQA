package com.aqa.extraction;

import com.aqa.extraction.RelationExtractorInterface;

import java.util.List;

public abstract class RelationExtractor implements RelationExtractorInterface {


    // Take all strings in list and build them into one string
    protected String createStringFromList(List<String> words){
        StringBuilder sb = new StringBuilder();
        
        // Add the first element of the list, and remove it
        sb.append(words.get(0));
        
        for(String s : words.subList(1, words.size())){
            if(s.equals(","))
                sb.append(s);
            else
                sb.append(" " + s);
        }
        
        return sb.toString();
    }

    // Find the index of the phrase in words
    protected int indexOfPhrase(List<String> words, List<String> phrase){
        int index = -1;
        int wordsSize = words.size();
        int phraseSize = phrase.size();

        for(int i = 0; i < wordsSize - phraseSize; i++){
            if(words.get(i).equals(phrase.get(0))){
                boolean flag = true;
                for(int j = 0; j < phraseSize; j++){
                    if(!words.get(j + i).equals(phrase.get(j))){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}

