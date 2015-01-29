
package sga;

import optimization.Copyable;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class NoMutation<Individual extends Copyable<Individual>> implements MutationPerformer<Individual>
{

    @Override
    public Population<Individual> mutation(Population<Individual> population, double thetaM)
    {
        return population;
    }

    @Override
    public void reset()
    {
    }

}
