
package flowshop;

import optimization.Function;
import optimization.Permutation;
import sga.LocalSearch;
import sga.Population;
import sga.SimplePopulation;
import simplealgs.AllTranspositions;
import simplealgs.HillClimbing;
import simplealgs.TranspositionChooser;

/**
 *
 * @author Grzegorz Los
 */
public class LocalSearchByHC implements LocalSearch<Permutation>
{
    final HillClimbing<Permutation> hc;

    public LocalSearchByHC(int nrOfNeighbours)
    {
        if (nrOfNeighbours <= 0)
            hc = new HillClimbing<>(1, null, new AllTranspositions());        
        else
            hc = new HillClimbing<>(1, null, new TranspositionChooser(nrOfNeighbours));
    }    
    
    @Override
    public Population<Permutation> upgrade(Population<Permutation> pop, Function<Permutation> F)
    {
        SimplePopulation<Permutation> newPop = new SimplePopulation<>();
        for (int i = 0; i < pop.getSize(); ++i)
            newPop.addIndividual(hc.oneTry(pop.getIndividual(i), F));
        return newPop;
    }

}
