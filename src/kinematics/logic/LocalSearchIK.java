
package kinematics.logic;

import sga.Function;
import sga.LocalSearch;
import sga.Population;
import sga.RandomnessSource;

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
    public void upgrade(Population<Configuration> pop, Function<Configuration> F)
    {
        for (int i = 0; i < pop.getSize(); ++i)
            upgrade(pop.getIndividual(i), F);
    }
    
    public void upgrade(Configuration conf, Function<Configuration> F)
    {
        int k = RandomnessSource.rand.nextInt(conf.angle.length);
        double alfa = pData.sData.alfa[k];
        double beta = pData.sData.beta[k];
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