
package sga;

import optimization.Copyable;
import simplealgs.RandIndChooser;

/**
 *
 * @author Grzegorz Los
 */
public class SimpleRandomPopulationGenerator<Individual extends Copyable<Individual>> implements RandomPopulationGenerator<Individual>
{
    private final RandIndChooser<Individual> randInd;

    public SimpleRandomPopulationGenerator(RandIndChooser<Individual> randInd)
    {
        this.randInd = randInd;
    }

    @Override
    public Population<Individual> generate(int N)
    {
        SimplePopulation<Individual> population = new SimplePopulation<>();
        for (int i = 0; i < N; ++i)
            population.addIndividual( randInd.getNext() );
        return population;
    }

}
