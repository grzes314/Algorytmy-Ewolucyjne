
package pbil;

/**
 *
 * @author grzes
 */
public class PBIL
{

    public PBIL()
    {
        learnRate = 0.001;
        mutationProb = 0;
        mutationRate = 0.001;
    }

    public PBIL(double learnRate, double mutationProb, double mutationRate)
    {
        this.learnRate = learnRate;
        this.mutationProb = mutationProb;
        this.mutationRate = mutationRate;
    }
    
    public void run(PopulationGenerator popGen, TargetFunction fun, int popSize)
    {
        popGen.createInitialDistrs();
        Population pop = popGen.generate(popSize);
        pop.evaluate(fun);
        for (int i = 0; i < maxIterations; ++i)
        {
            if ( pop.terminationConditionSatisfied() )
                break;
            
            Individual ind = pop.getBestIndividual();
            popGen.improve(ind, learnRate);
            popGen.mutate(mutationProb, mutationRate);
            pop = popGen.generate(popSize);
            pop.evaluate(fun);
        }
    }

    public double getLearnRate() {
        return learnRate;
    }

    public void setLearnRate(double learnRate) {
        this.learnRate = learnRate;
    }

    public double getMutationProb() {
        return mutationProb;
    }

    public void setMutationProb(double mutationProb) {
        this.mutationProb = mutationProb;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
    
    private int maxIterations;
    private double learnRate;
    private double mutationProb;
    private double mutationRate;
}
