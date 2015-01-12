
package simplealgs;

import java.util.List;

/**
 *
 * @author Grzegorz Los
 */
public interface NeighbourhoodChooser<Individual>
{
    List<Individual> choose(Individual ind);
}
