
package kinematics.logic;

import java.util.ArrayList;
import optimization.Copyable;
import optimization.ValuedIndividual;
import sga.Population;
import sga.ProgressObserver;
import sga.ReplacementPerformer;
import sga.SimplePopulation;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class ReplacementWithNonFeasible<Individual extends Copyable<Individual>>
                implements  ReplacementPerformer<Individual>,
                            ProgressObserver
{
    private final int maxNonFeasible;
    private final int maxFeasible;
    private final double initialMaxInfeasibility;
    private double maxInfeasibility;

    public ReplacementWithNonFeasible(int maxFeasible, int maxNonFeasible, double initialMaxInfeasibility)
    {
        this.maxNonFeasible = maxNonFeasible;
        this.maxFeasible = maxFeasible;
        this.initialMaxInfeasibility = initialMaxInfeasibility;
        maxInfeasibility = initialMaxInfeasibility;
    }

    @Override
    public Population<Individual> replace(Population<Individual> old, Population<Individual> _new)
    {
        SimplePopulation<Individual> newest = new SimplePopulation<>();
        ArrayList<ValuedIndividual<Individual>> a =  old.createListOfSortedIndividuals();
        ArrayList<ValuedIndividual<Individual>> b = _new.createListOfSortedIndividuals();
        int ai = 0, bi = 0;
        int feasible = 0;
        int nonfeasible = 0;
        while ( (feasible < maxFeasible || nonfeasible < maxNonFeasible)
            && (ai < old.getSize() || bi < _new.getSize()) )
        {
            ValuedIndividual<Individual> fromA = ai < old.getSize() ? a.get(ai) : null;
            ValuedIndividual<Individual> fromB = bi < _new.getSize() ? b.get(bi) : null;
            ValuedIndividual<Individual> better = getBetter(fromA, fromB);
            if (better.feasible && feasible < maxFeasible)
            {
                newest.addIndividual(better);
                feasible++;
            }
            if (!better.feasible && nonfeasible < maxNonFeasible && better.infeasibility < maxInfeasibility)
            {
                newest.addIndividual(better);
                nonfeasible++;
            }
            if (better == fromA) ai++;
            else bi++;
        }
        return newest;
    }

    private ValuedIndividual<Individual> getBetter(ValuedIndividual<Individual> fromA, ValuedIndividual<Individual> fromB)
    {
        if (fromA == null)
            return fromB;
        if (fromB == null)
            return fromA;
        if (fromA.value > fromB.value)
            return fromA;
        else return fromB;
    }

    @Override
    public void currentIteration(int i, boolean solutionImproved)
    {
        if (solutionImproved)
            maxInfeasibility *= 1.005;
        else
            maxInfeasibility *= 0.999;
        if (i % 200 == 0)
            System.out.println("max infeasibility: " + maxInfeasibility);
    }
}
