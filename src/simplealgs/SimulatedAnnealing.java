
package simplealgs;

import optimization.Function;
import optimization.RandomnessSource;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class SimulatedAnnealing<Individual>
{
    private RandIndChooser<Individual> randInd;
    private NeighbourhoodChooser<Individual> neighbourhood;
    private Function<Individual> F;
    private int trials;
    private int nrOfIterations;

    public SimulatedAnnealing(int trials, int nrOfIterations)
    {
        this.trials = trials;
        this.nrOfIterations = nrOfIterations;
    }

    public SimulatedAnnealing(int trials, int nrOfIterations, RandIndChooser<Individual> randInd, NeighbourhoodChooser<Individual> neighbourhood)
    {
        this.trials = trials;
        this.nrOfIterations = nrOfIterations;
        this.randInd = randInd;
        this.neighbourhood = neighbourhood;
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

    public int getNrOfIterations()
    {
        return nrOfIterations;
    }

    public void setNrOfIterations(int nrOfIterations)
    {
        if (nrOfIterations < 1)
            this.nrOfIterations = 1;
        else
           this.nrOfIterations = nrOfIterations;
    }

    public ValuedIndividual<Individual> maximize(Function<Individual> F)
    {
        this.F = F;
        ValuedIndividual<Individual> currBest = oneTry();
        for (int i = 1; i < trials; ++i)
        {
            ValuedIndividual vi = oneTry();
            if (vi.value > currBest.value)
                currBest = vi;
        }
        return currBest;
    }
    
    public ValuedIndividual<Individual> oneTry()
    {
        Individual ind = randInd.getNext();
        ValuedIndividual<Individual> curr  = F.value(ind);
        ValuedIndividual<Individual> best  = curr;
        for (int i = 0; i < nrOfIterations; ++i)
        {
            double coeff = nrOfIterations / 1000000.0;
            double t = 1 / (1.0 + Math.sqrt(i/coeff));
            curr = oneIteration(curr, t, i);
            if (curr.value > best.value)
                best = curr;
        }
        return best;
    }

    private ValuedIndividual<Individual> oneIteration(ValuedIndividual<Individual> curr, double temp, int i)
    {
        Individual n = neighbourhood.chooseOne(curr.ind);
        ValuedIndividual vi = F.value(n);
        if (vi.value > curr.value)
            return vi;
        double r = RandomnessSource.rand.nextDouble();
        double x = (vi.value - curr.value) / Math.abs(curr.value) / temp; // vi.value < curr.value
        if (Math.exp(x) > r)
        {
            if (x != 0)
                System.out.println("przeskok: " + i + ", temp=" + temp + ", rel= " + (vi.value - curr.value) / Math.abs(curr.value));
            return vi;
        }
        else
        {
            //System.out.println("bezzmian: " + i + ", temp=" + temp + ", rel= " + (vi.value - curr.value) / Math.abs(curr.value));
            return curr;
        }
    }
}
