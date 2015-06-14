
package msi;

import optimization.Permutation;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class Valuator implements optimization.Function<Permutation>
{
    public final ProblemData pd;

    public Valuator(ProblemData pd)
    {
        this.pd = pd;
    }
    
    @Override
    public ValuedIndividual<Permutation> value(Permutation x)
    {
        int nrOfTacts = pd.nrOfTasks + pd.nrOfMachines;
        double tactLength = 0;
        for (int k = 0; k < nrOfTacts; ++k)
        {
            int minI = Integer.max(k - pd.nrOfMachines + 1, 0);
            int maxI = Integer.min(k, pd.nrOfTasks - 1);
            double time;
            for (int i = minI; i <= maxI; ++i)
            {
                time = calcTime(k, i, x);
                if (tactLength > time)
                    tactLength = time;
            }
        }
        return new ValuedIndividual<>(x, tactLength);
    }
    
    private double calcTime(int k, int i, Permutation x)
    {
        int m = k - i;
        int z = x.at(i);
        double ex = pd.exTimes[m][z];
        double prep = 0;
        if (i > 0)
            prep = pd.prepTimes[m][x.at(i-1)][z];
        return -ex - prep;
    }
}
