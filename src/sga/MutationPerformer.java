
package sga;

/**
 *
 * @author Grzegorz Los
 */
public interface MutationPerformer<Individual extends Copyable<Individual>>
{
    public Population<Individual> mutation(Population<Individual> population, double thetaM);
}
