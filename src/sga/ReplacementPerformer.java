
package sga;

import optimization.Copyable;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface ReplacementPerformer<Individual extends Copyable<Individual>>
{
    public Population<Individual> replace(Population<Individual> old, Population<Individual> _new);
}
