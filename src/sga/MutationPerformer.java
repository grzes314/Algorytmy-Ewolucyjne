
package sga;

import optimization.Copyable;

/**
 *
 * @author Grzegorz Los
 */
public interface MutationPerformer<Individual extends Copyable<Individual>>
{
    public Population<Individual> mutation(Population<Individual> population, double thetaM);
    
    public void reset();
}
