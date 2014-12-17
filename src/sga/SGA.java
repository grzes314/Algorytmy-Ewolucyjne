
package sga;

import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author Grzegorz Los
 * @param <Individual> type of an individuals in population.
 */
public class SGA<Individual>
{
    //------------------------ Algorithm parameters -----------------------------------------------
    private final int populationSize;
    private final int nrOfParents;  // must be at least the size of populationSize
    private final double thetaC;
    private final double thetaM;
    private final int maxIterations;
    
    //-------------------- Objects responsible for steps of the algorithm --------------------------
    private RandomPopulationGenerator<Individual> randomPopoluationGenerator;
    private ParentSelector<Individual> parentSelector;
    private CrossoverPerformer<Individual> crossoverPerformer;
    private MutationPerformer<Individual> mutationPerformer;
    private ReplacementPerformer<Individual> replacementPerformer;
    
    //------------------------------- Statistics ---------------------------------------------------
    private int performedIterations;
    private ArrayList<Double> worst, best, mean;
    private Population<Individual> currPopulation;
    private double bestVal;
    private Individual bestInd;
    
    //------------------- Other -------------------------------------------------
    private final ArrayList<ProgressObserver> observers = new ArrayList<>();

    public SGA(SGA_Params params)
    {
        populationSize = params.populationSize;
        nrOfParents = params.nrOfParents;
        thetaC = params.thetaC;
        thetaM = params.thetaM;
        maxIterations = params.maxIterations;
    }
    
    public void maximize(Function<Individual> F)
    {
        initStats();
        currPopulation = randomPopoluationGenerator.generate(populationSize);
        currPopulation.evaluate(F);
        updateStats(0);
        for (int i = 1; i <= maxIterations && !currPopulation.terminationCondition(); ++i)
        {
            postObservers(i);
            Population populationP = parentSelector.select(currPopulation, nrOfParents);
            Population populationC = crossoverPerformer.crossover(populationP, thetaC);
            populationC = mutationPerformer.mutation(populationC, thetaM);
            populationC.evaluate(F);
            currPopulation = replacementPerformer.replace(currPopulation, populationC);
            currPopulation.evaluate(F);
            updateStats(i);
        }
    }

    public int getMaxIterations()
    {
        return maxIterations;
    }

    public RandomPopulationGenerator getRandomPopoluationGenerator()
    {
        return randomPopoluationGenerator;
    }

    public void setRandomPopoluationGenerator(RandomPopulationGenerator randomPopoluationGenerator)
    {
        this.randomPopoluationGenerator = randomPopoluationGenerator;
    }

    public ParentSelector getParentSelector()
    {
        return parentSelector;
    }

    public void setParentSelector(ParentSelector parentSelector)
    {
        this.parentSelector = parentSelector;
    }

    public CrossoverPerformer getCrossoverPerformer()
    {
        return crossoverPerformer;
    }

    public void setCrossoverPerformer(CrossoverPerformer crossoverPerformer)
    {
        this.crossoverPerformer = crossoverPerformer;
    }

    public MutationPerformer getMutationPerformer()
    {
        return mutationPerformer;
    }

    public void setMutationPerformer(MutationPerformer mutationPerformer)
    {
        this.mutationPerformer = mutationPerformer;
    }

    public ReplacementPerformer getReplacementPerformer()
    {
        return replacementPerformer;
    }

    public void setReplacementPerformer(ReplacementPerformer replacementPerformer)
    {
        this.replacementPerformer = replacementPerformer;
    }

    public ArrayList<Double> getBest()
    {
        return best;
    }

    public ArrayList<Double> getWorst()
    {
        return worst;
    }

    public ArrayList<Double> getMean()
    {
        return mean;
    }

    public int getPerformedIterations()
    {
        return performedIterations;
    }

    public double getBestVal()
    {
        return bestVal;
    }
    
    public Individual getBestIndividual()
    {
        return bestInd;
    }

    private void initStats()
    {
        worst = new ArrayList<>();
        best = new ArrayList<>();
        mean = new ArrayList<>();
        performedIterations = 0;
        bestVal = Double.NEGATIVE_INFINITY;
        bestInd = null;
    }

    private void updateStats(int iteration)
    {
        double currBestVal = currPopulation.getMaxValue();
        worst.add( currPopulation.getMinValue() );
        best.add( currBestVal );
        mean.add( currPopulation.getMeanValue() );
        if (currBestVal > bestVal)
        {
            bestVal = currBestVal;
            bestInd = currPopulation.getMaxIndividual();
        }
        performedIterations = iteration;
    }
    
    public void addObserver(ProgressObserver obs)
    {
        observers.add(obs);
    }
    
    public void removeObserver(ProgressObserver obs)
    {
        observers.remove(obs);
    }
    
    private void postObservers(int i)
    {
        for (ProgressObserver obs: observers)
            obs.currentIteration(i);
    }
    
    public SGA_Result getResult()
    {
        return new SGA_Result(
            worst, best, mean,
            bestVal, bestInd,
            performedIterations
        );
    }
        
}
