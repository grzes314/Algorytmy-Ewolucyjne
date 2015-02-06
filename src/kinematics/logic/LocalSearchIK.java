
package kinematics.logic;

import optimization.Function;
import sga.LocalSearch;
import sga.Population;
import optimization.RandomnessSource;

/**
 *
 * @author Grzegorz Los
 */
public class LocalSearchIK implements LocalSearch<Configuration>
{
    private final ProblemData pData;

    public LocalSearchIK(ProblemData pData)
    {
        this.pData = pData;
    }

    @Override
    public Population<Configuration> upgrade(Population<Configuration> pop, Function<Configuration> F)
    {
        for (int i = 0; i < pop.getSize(); ++i)
            upgrade(pop.getIndividual(i).ind, F);
        return pop;
    }
    
    public void upgrade(Configuration conf, Function<Configuration> F)
    {
        int k = RandomnessSource.rand.nextInt(conf.angle.length);
        double alfa = pData.armData.get(k).minAngle;
        double beta = pData.armData.get(k).maxAngle;
        double bestAngle = conf.angle[k];
        double bestVal = F.value(conf).value;
        for (int i = 0; i <= 10; ++i)
        {
            double newAngle = alfa + i * (beta - alfa) / 10.0;
            conf.angle[k] = newAngle;
            double val = F.value(conf).value;
            if (val > bestVal)
            {
                bestAngle = newAngle;
                bestVal = val;
            }
        }
        conf.angle[k] = bestAngle;
    }

}
