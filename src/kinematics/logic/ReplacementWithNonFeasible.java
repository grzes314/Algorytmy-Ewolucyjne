
package kinematics.logic;

import java.util.ArrayList;
import sga.Population;
import sga.ReplacementPerformer;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class ReplacementWithNonFeasible<Individual> implements ReplacementPerformer<Individual>
{
    private final int maxNonFeasible;
    private final int maxFeasible;

    public ReplacementWithNonFeasible(int maxNonFeasible, int maxFeasible)
    {
        this.maxNonFeasible = maxNonFeasible;
        this.maxFeasible = maxFeasible;
    }

    @Override
    public Population<Individual> replace(Population<Individual> old, Population<Individual> _new)
    {
        PopulationWithNonFeasible<Individual> newest = new PopulationWithNonFeasible<>();
        ArrayList<ValuedIndividual<Individual>> a =  old.createListOfSortedIndividuals();
        ArrayList<ValuedIndividual<Individual>> b = _new.createListOfSortedIndividuals();
        int N = old.getSize();
        int M = _new.getSize();
        int ai = 0, bi = 0;
        int feasible = 0;
        int nonfeasible = 0;
        for (int i = 0; feasible < maxFeasible; ++i)
        {
            ValuedIndividual<Individual> fromA = ai < old.getSize() ? a.get(ai) : null;
            ValuedIndividual<Individual> fromB = bi < _new.getSize() ? b.get(bi) : null;
            ValuedIndividual<Individual> better = getBetter(fromA, fromB);
            if (better.feasible && feasible < maxFeasible)
            {
                newest.addIndividual(better.ind);
                feasible++;
            }
            if (!better.feasible && nonfeasible < maxNonFeasible)
            {
                newest.addIndividual(better.ind);
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

}
