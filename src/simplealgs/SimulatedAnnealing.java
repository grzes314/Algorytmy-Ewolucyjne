
package simplealgs;

import java.util.Random;
import optimization.Function;
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
    private int nrOfIterations;
    private final Random rand;

    public SimulatedAnnealing(int nrOfIterations)
    {
        this.nrOfIterations = nrOfIterations;
        rand = new Random();
    }

    public SimulatedAnnealing(int nrOfIterations, RandIndChooser<Individual> randInd, NeighbourhoodChooser<Individual> neighbourhood)
    {
        this.randInd = randInd;
        this.neighbourhood = neighbourhood;
        this.nrOfIterations = nrOfIterations;
        rand = new Random();
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
        Individual ind = randInd.getNext();
        int iter = 0;
        ValuedIndividual<Individual> curr  = F.value(ind);
        ValuedIndividual<Individual> best  = curr;
        for (int i = 0; i < nrOfIterations; ++i)
        {
            double t = 1.0 / (1.0 + Math.log(1.0 + i));
            curr = oneIteration(curr, t);
            if (curr.value > best.value)
                best = curr;
        }
        return best;
    }

    private ValuedIndividual<Individual> oneIteration(ValuedIndividual<Individual> curr, double temp)
    {
        Individual n = neighbourhood.chooseOne(curr.ind);
        ValuedIndividual vi = F.value(n);
        if (vi.value > curr.value)
            return vi;
        double r = rand.nextDouble();
        double x = (vi.value - curr.value) / temp; //curr.value >= vi.value
        if (Math.exp(x) > r)
            return vi;
        else
            return curr;
    }
}
