
package sga;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface CrossoverPerformer<Individual>
{
    public Population<Individual> crossover(Population<Individual> parents, double thetaC);
}
