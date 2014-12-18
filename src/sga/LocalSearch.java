
package sga;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface LocalSearch<Individual>
{
    public void upgrade(Population<Individual> pop, Function<Individual> F);
}
