
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
    private boolean fast = true;

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
    
    public ValuedIndividual<Individual> maximize(Function<Individual> F)
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

    private ValuedIndividual<Individual> oneTry()
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

    public ValuedIndividual<Individual> oneTry(ValuedIndividual<Individual> currInd, Function<Individual> F)
    {
        this.F = F;
        ValuedIndividual<Individual> curr = F.value(currInd.ind);
        ValuedIndividual<Individual> prev;
        do
        {
            prev = curr;
            curr = climb(curr);
        } while (curr.value - prev.value > eps);
        return curr;
    }
    
    private ValuedIndividual<Individual> climb(final ValuedIndividual<Individual> curr)
    {
        ValuedIndividual<Individual> bestNeigh = curr;
        List<Individual> neighs = neighbourhood.choose(curr.ind);
        for (Individual n: neighs)
        {
            ValuedIndividual<Individual> v = F.value(n);
            if (v.value > bestNeigh.value)
            {
                if (fast)
                    return v;
                bestNeigh = v;
            }
        }
        return bestNeigh;
    }
}
