package com.blogspot.sirineslihan;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.dict.DictionaryEntry;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

public class MovieSearch {
	static final double CHUNK_SCORE = 1.0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MapDictionary<String> dictionary = new MapDictionary<String>();
		dictionary.addEntry(new DictionaryEntry<String>("50","PERSON",CHUNK_SCORE));
	    dictionary.addEntry(new DictionaryEntry<String>("50 cent","PERSON",CHUNK_SCORE));
	    dictionary.addEntry(new DictionaryEntry<String>("cents","DB_ID_1232",CHUNK_SCORE));
	    dictionary.addEntry(new DictionaryEntry<String>("cent","MONETARY_UNIT",CHUNK_SCORE));
	    dictionary.addEntry(new DictionaryEntry<String>("dvd player","PRODUCT",CHUNK_SCORE));
	    
	    ExactDictionaryChunker dictionaryChunkerTT
        = new ExactDictionaryChunker(dictionary,
                                     IndoEuropeanTokenizerFactory.INSTANCE,
                                     true,true);
	    String text = "I found 50 cents'ten but bigger cent.";
	    chunk(dictionaryChunkerTT,text);
	}
	static void chunk(ExactDictionaryChunker chunker, String text) {
	    Chunking chunking = chunker.chunk(text);
	    for (Chunk chunk : chunking.chunkSet()) {
	        int start = chunk.start();
	        int end = chunk.end();
	        String type = chunk.type();
	        double score = chunk.score();
	        String phrase = text.substring(start,end);
	        System.out.println("     phrase=|" + phrase + "|"
	                           + " start=" + start
	                           + " end=" + end
	                           + " type=" + type
	                           + " score=" + score);
	    }
	}
}
