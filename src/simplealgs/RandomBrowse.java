
package simplealgs;

import java.util.List;
import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class RandomBrowse<Individual>
{
    private RandIndChooser<Individual> randInd;
    private Function<Individual> F;
    private int trials;

    public RandomBrowse(int trials, RandIndChooser<Individual> randInd)
    {
        this.randInd = randInd;
        this.trials = trials;
    }

    public RandomBrowse(int trials)
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
        ValuedIndividual currBest = new ValuedIndividual(null, Double.NEGATIVE_INFINITY);
        for (int i = 0; i < trials; ++i)
        {
            Individual ind = randInd.getNext();
            ValuedIndividual vi = F.value(ind);
            if (vi.value > currBest.value)
                currBest = vi;
        }
        return currBest;
    }
}
