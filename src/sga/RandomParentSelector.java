
package sga;

import optimization.Copyable;
import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class RandomParentSelector<Individual extends Copyable<Individual>> implements ParentSelector<Individual>
{
    @Override
    public Population<Individual> select(Population<Individual> population, int nrOfParents)
    {
        SimplePopulation<Individual> parents = new SimplePopulation<>();
        if (population.getSize() == 0)
            return parents;
        for (int i = 0; i < nrOfParents; ++i)
            parents.addIndividual( population.getIndividual(rand.nextInt(population.getSize())) );
        return parents;
    }
}
