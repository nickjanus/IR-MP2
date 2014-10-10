package edu.illinois.cs.index.similarities;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class PivotedLength extends SimilarityBase {
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
        float s = (float)0.5; //[0,1]
        score *= (1 + Math.log(1 + Math.log(termFreq)))/(1 - s + s*(docLength/stats.getAvgFieldLength()));
        score *= Math.log((stats.getNumberOfDocuments() + 1)/stats.getDocFreq());
    	return score;
    }

    @Override
    public String toString() {
        return "Pivoted Length Normalization";
    }

}
