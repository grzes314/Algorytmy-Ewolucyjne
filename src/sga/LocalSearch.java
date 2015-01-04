
package sga;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface LocalSearch<Individual extends Copyable<Individual>>
{
    public void upgrade(Population<Individual> pop, Function<Individual> F);
}
