
package sga;

/**
 *
 * @author Grzegorz Los
 */
public interface ParentSelector<Individual extends Copyable<Individual>>
{
    public Population<Individual> select(Population<Individual> population, int nrOfParents);
}
