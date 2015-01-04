
package sga;

/**
 *
 * @author Grzegorz Los
 */
public interface RandomPopulationGenerator<Individual extends Copyable<Individual>>
{
    /**
     * Generates population.
     * @param N number of individuals in population.
     * @return generated population.
     */
    public Population<Individual> generate(int N);
}
