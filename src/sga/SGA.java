
package sga;

import optimization.Copyable;
import optimization.Function;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 * @param <Individual> type of an individuals in population.
 */
public class SGA<Individual extends Copyable<Individual>>
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
    private LocalSearch<Individual> localSearch = new NoLocalSearch<>();
    
    //------------------------------- Statistics ---------------------------------------------------
    private int performedIterations;
    private ArrayList<Double> worst, best, mean;
    private Population<Individual> currPopulation;
    private double bestVal;
    private Individual bestInd;
    private double currBestVal;
    private Individual currBestInd;
    
    //------------------- Other -------------------------------------------------
    private final ArrayList<ProgressObserver> observers = new ArrayList<>();
    private boolean interrupted;
    private boolean solutionImproved;

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
        interrupted = false;
        currPopulation = randomPopoluationGenerator.generate(populationSize);
        currPopulation = localSearch.upgrade(currPopulation, F);
        currPopulation.evaluate(F);
        updateStats(0);
        for (int i = 1; i <= maxIterations && !interrupted && !currPopulation.terminationCondition(); ++i)
        {
            solutionImproved = false;
            Population populationP = parentSelector.select(currPopulation, nrOfParents);
            Population populationC = crossoverPerformer.crossover(populationP, thetaC);
            if (currBestInd == null)
                mutationPerformer.reset();
            populationC = mutationPerformer.mutation(populationC, thetaM);
            populationC.evaluate(F);
            currPopulation = replacementPerformer.replace(currPopulation, populationC);
            if (i % 200 == 0)
                currPopulation = localSearch.upgrade(currPopulation, F);
            currPopulation.evaluate(F);
            updateStats(i);
            postObservers(i);
        }
    }
    
    public void interrupt()
    {
        interrupted = true;
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

    public LocalSearch<Individual> getLocalSearch()
    {
        return localSearch;
    }

    public void setLocalSearch(LocalSearch<Individual> localSearch)
    {
        this.localSearch = localSearch;
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
        return bestInd == null ? null : bestInd;
    }

    public double getCurrBestVal()
    {
        return currBestVal;
    }

    public Individual getCurrBestInd()
    {
        return currBestInd;
    }
    
    public Population<Individual> getCurrPopulation()
    {
        return currPopulation;
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
        currBestVal = currPopulation.getMaxValue();
        currBestInd = currPopulation.getMaxIndividual();
        worst.add( currPopulation.getMinValue() );
        best.add( currBestVal );
        mean.add( currPopulation.getMeanValue() );
        if (currBestVal > bestVal)
        {
            solutionImproved = true;
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
        new Thread( () -> {
            for (ProgressObserver obs: observers)
                obs.currentIteration(i, solutionImproved);
        }).start();
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
