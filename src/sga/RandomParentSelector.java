
package sga;

import static sga.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class RandomParentSelector<Individual> implements ParentSelector<Individual>
{
    @Override
    public Population<Individual> select(Population<Individual> population, int nrOfParents)
    {
        SimplePopulation<Individual> parents = new SimplePopulation<>();
        for (int i = 0; i < nrOfParents; ++i)
            parents.addIndividual( population.getIndividual(rand.nextInt(population.getSize())) );
        return parents;
    }
}
