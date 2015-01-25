
package sga;

import optimization.Copyable;
import optimization.Function;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface LocalSearch<Individual extends Copyable<Individual>>
{
    public Population<Individual> upgrade(Population<Individual> pop, Function<Individual> F);
}
