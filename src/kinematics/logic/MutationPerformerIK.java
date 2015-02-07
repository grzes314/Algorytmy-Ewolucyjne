
package kinematics.logic;

import sga.Population;
import sga.ProgressObserver;
import static optimization.RandomnessSource.rand;
import sga.SimplePopulation;

/**
 *
 * @author Grzegorz Los
 */
public class MutationPerformerIK implements sga.MutationPerformer<Configuration>, ProgressObserver
{
    private final ArmData armData;
    private final int nrOfLastIterationsObserved;
    private final boolean[] succ;
    private int sum = 0;
    private double range = Math.PI / 8;

    public MutationPerformerIK(ArmData armData, int nrOfLastIterationsObserved)
    {
        this.armData = armData;
        this.nrOfLastIterationsObserved = nrOfLastIterationsObserved;
        succ = new boolean[nrOfLastIterationsObserved];
    }

    @Override
    public Population<Configuration> mutation(Population<Configuration> population, double thetaM)
    {
        SimplePopulation<Configuration> mutated = new SimplePopulation<>();
        int N = population.getSize();
        for (int i = 0; i < N; ++i)
        {
            mutated.addIndividual( mutate(population.getIndividual(i).ind, thetaM) );
        }
        return mutated;
    }

    private Configuration mutate(Configuration individual, double thetaM)
    {
        int n = armData.getSize();
        Configuration c = new Configuration(n);
        for (int i = 0; i < n; ++i)
        {
            c.angle[i] = individual.angle[i];
            if (rand.nextDouble() < thetaM)
            {
                double d = rand.nextDouble() * 2 * range - range;
                OneSegment s = armData.get(i);
                c.angle[i] += d;
                if (c.angle[i] > s.maxAngle)
                    c.angle[i] = s.maxAngle;
                if (c.angle[i] < s.minAngle)
                    c.angle[i] = s.minAngle;
            }
        }
        return c;
    }

    @Override
    public void currentIteration(int i, boolean solutionImproved)
    {
        boolean prev = succ[i % nrOfLastIterationsObserved];
        succ[i % nrOfLastIterationsObserved] = solutionImproved;
        if (prev && !solutionImproved)
            sum--;
        if (!prev && solutionImproved)
            sum++;
        if (sum < 0 || sum > nrOfLastIterationsObserved)
            throw new RuntimeException("Internal error");
        if (sum < nrOfLastIterationsObserved / 5)
            range *= 0.999;
        else
            range *= 1.05;
        if (i % 200 == 0)
            System.out.printf("Range =  %.3frad = %4.2f degrees\n", range, 180*range/Math.PI);
    }

    @Override
    public void reset()
    {
        range = Math.PI/8;
    }
    
}
