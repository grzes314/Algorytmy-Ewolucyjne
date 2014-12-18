
package kinematics.logic;

import sga.CrossoverPerformer;
import sga.Population;

/**
 *
 * @author Grzegorz Los
 */
public class NoCrossoverIK implements CrossoverPerformer<Configuration>
{

    @Override
    public Population<Configuration> crossover(Population<Configuration> parents, double thetaC)
    {
        return parents;
    }

}
