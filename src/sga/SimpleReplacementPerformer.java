
package sga;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class SimpleReplacementPerformer<Individual> implements ReplacementPerformer<Individual>
{

    @Override
    public Population<Individual> replace(Population<Individual> old, Population<Individual> _new)
    {
        SimplePopulation<Individual> newest = new SimplePopulation<>();
        ArrayList<ValuedIndividual<Individual>> a =  old.createListOfSortedIndividuals();
        ArrayList<ValuedIndividual<Individual>> b = _new.createListOfSortedIndividuals();
        int N = old.getSize();
        int M = _new.getSize();
        int ai = 0, bi = 0;
        for (int i = 0; i < N; ++i)
        {
            if (ai == N) {
                newest.addIndividual( b.get(bi).ind );
                bi++;
            } else if (bi == M) {
                newest.addIndividual( a.get(ai).ind );
                ai++;
            } else if (a.get(ai).value > b.get(bi).value) {
                newest.addIndividual( a.get(ai).ind );
                ai++;
            } else {
                newest.addIndividual( b.get(bi).ind );
                bi++;
            }
        }
        return newest;
    }

}
