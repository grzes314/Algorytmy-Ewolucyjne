
package sga;

import optimization.Copyable;
import optimization.Function;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class NoLocalSearch<Individual extends Copyable<Individual>> implements LocalSearch<Individual>
{

    @Override
    public Population<Individual> upgrade(Population<Individual> pop, Function<Individual> F)
    {
        return pop;
    }

}
