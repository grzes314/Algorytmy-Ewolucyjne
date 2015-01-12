
package simplealgs;

import java.util.List;
import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class HillClimbing<Individual>
{
    private RandIndChooser<Individual> randInd;
    private NeighbourhoodChooser<Individual> neighbourhood;
    private Function<Individual> F;
    private static double eps = 1e-5;
    private int trials;

    public HillClimbing(int trials, RandIndChooser<Individual> randInd, NeighbourhoodChooser<Individual> neighbourhood)
    {
        this.randInd = randInd;
        this.neighbourhood = neighbourhood;
        this.trials = trials;
    }

    public HillClimbing(int trials)
    {
        this.trials = trials;
    }

    public RandIndChooser<Individual> getRandInd()
    {
        return randInd;
    }

    public void setRandInd(RandIndChooser<Individual> randInd)
    {
        this.randInd = randInd;
    }

    public NeighbourhoodChooser<Individual> getNeighbourhood()
    {
        return neighbourhood;
    }

    public void setNeighbourhood(NeighbourhoodChooser<Individual> neighbourhood)
    {
        this.neighbourhood = neighbourhood;
    }

    public int getTrials()
    {
        return trials;
    }

    public void setTrials(int trials)
    {
        if (trials < 1)
            this.trials = 1;
        else
            this.trials = trials;
        
    }
    
    ValuedIndividual maximize(Function<Individual> F)
    {
        this.F = F;
        ValuedIndividual currBest = oneTry();
        for (int i = 1; i < trials; ++i)
        {
            ValuedIndividual vi = oneTry();
            if (vi.value > currBest.value)
                currBest = vi;
        }
        return currBest;
    }

    private ValuedIndividual oneTry()
    {
        Individual currInd = randInd.getNext();
        ValuedIndividual<Individual> curr = F.value(currInd);
        ValuedIndividual<Individual> prev;
        do
        {
            prev = curr;
            curr = climb(curr);
        } while (curr.value - prev.value > eps);
        return curr;
    }
    
    private ValuedIndividual climb(final ValuedIndividual<Individual> curr)
    {
        ValuedIndividual<Individual> bestNeigh = new ValuedIndividual<>(null, Double.NEGATIVE_INFINITY);
        List<Individual> neighs = neighbourhood.choose(curr.ind);
        for (Individual n: neighs)
        {
            ValuedIndividual<Individual> v = F.value(n);
            if (v.value > bestNeigh.value)
                bestNeigh = v;
        }
        return bestNeigh;
    }
}
