package edu.illinois.cs.index.similarities;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.LMSimilarity;

public class DirichletPrior extends LMSimilarity {

    private LMSimilarity.DefaultCollectionModel model; // this would be your reference model
    private float queryLength = 0; // will be set at query time automatically

    public DirichletPrior() {
        model = new LMSimilarity.DefaultCollectionModel();
    }

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
    	float score = 1, smoothing = 0;
        float mew = (float)40; //>0
        float alpha = mew/(mew + docLength);
        smoothing = (termFreq + mew*model.computeProbability(stats))/(docLength + mew);
        score = (float) (Math.log(smoothing/(alpha*model.computeProbability(stats))) + Math.log(alpha)); //approximation of |q|
    	return score;
    }

    @Override
    public String getName() {
        return "Dirichlet Prior";
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setQueryLength(float length) {
        queryLength = length;
    }
}
