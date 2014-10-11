package edu.illinois.cs.index.similarities;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class OkapiBM25 extends SimilarityBase {
    /**
     * Returns a score for a single term in the document.
     *
     * @param stats
     *            Provides access to corpus-level statistics
     * @param termFreq
     * @param docLength
     */
    @Override
    protected float score(BasicStats stats, float termFreq, float docLength) {
        float score = 1;
    	float k1 = (float) 1.2; //[1.2,2]
    	float k2 = 100; //(0,1000]
    	float b  = (float) 0.75; //[0.75,1.2]
    	float docFreq = stats.getDocFreq();
    	float queryTermFreq = 1;
    	float numDocs = stats.getNumberOfDocuments();
    	
    	score *= Math.log((numDocs - docFreq + 0.5)/(docFreq + 0.5));
    	score *= ((k1 + 1)*termFreq)/(k1*(1 - b + b*(docLength/stats.getAvgFieldLength())) + termFreq);
    	score *= ((k2 + 1)*queryTermFreq)/(k2 + queryTermFreq);
    	
    	return score;
    }

    @Override
    public String toString() {
        return "Okapi BM25";
    }

}
