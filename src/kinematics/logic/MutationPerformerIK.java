
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
    private final ProblemData pData;
    private final int nrOfLastIterationsObserved;
    private final boolean[] succ;
    private int sum = 0;
    private double range = Math.PI / 8;

    public MutationPerformerIK(ProblemData pData, int nrOfLastIterationsObserved)
    {
        this.pData = pData;
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
            mutated.addIndividual( mutate(population.getIndividual(i), thetaM) );
        }
        return mutated;
    }

    private Configuration mutate(Configuration individual, double thetaM)
    {
        int n = pData.sData.n;
        Configuration c = new Configuration(n);
        for (int i = 0; i < n; ++i)
        {
            c.angle[i] = individual.angle[i];
            if (rand.nextDouble() < thetaM)
            {
                double d = rand.nextDouble() * 2 * range - range;
                c.angle[i] += d;
                if (c.angle[i] > pData.sData.beta[i])
                    c.angle[i] = pData.sData.beta[i];
                if (c.angle[i] < pData.sData.alfa[i])
                    c.angle[i] = pData.sData.alfa[i];
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
