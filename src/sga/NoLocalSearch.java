
package sga;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class NoLocalSearch<Individual extends Copyable<Individual>> implements LocalSearch<Individual>
{

    @Override
    public void upgrade(Population<Individual> pop, Function<Individual> F)
    {
    }

}
