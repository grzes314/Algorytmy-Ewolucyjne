
package kinematics.logic;

import sga.SimplePopulation;
import java.util.ArrayList;
import sga.Copyable;
import sga.Population;
import sga.ReplacementPerformer;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class ReplacementWithNonFeasible<Individual extends Copyable<Individual>> implements ReplacementPerformer<Individual>
{
    private final int maxNonFeasible;
    private final int maxFeasible;

    public ReplacementWithNonFeasible(int maxFeasible, int maxNonFeasible)
    {
        this.maxNonFeasible = maxNonFeasible;
        this.maxFeasible = maxFeasible;
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
            if (!better.feasible && nonfeasible < maxNonFeasible)
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
}
