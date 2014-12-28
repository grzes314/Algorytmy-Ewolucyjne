
package kinematics.logic;

import java.util.ArrayList;
import sga.Function;
import sga.Population;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class PopulationWithNonFeasible<Individual> implements Population<Individual>
{
    private final ArrayList<Individual> inds = new ArrayList<>();
    private double targetVals[], fitVals[], cumFitVals[];
    private boolean feasible[];
    private double minTargetVal, maxTargetVal, meanTargetVal;
    Individual maxIndividual, minIndividual;
    private int N;
    
    @Override
    public void evaluate(Function<Individual> F)
    {
        N = inds.size();
        calculateTargetVals(F);
        calculateStats();   // This order is important!!!111111111111
        calculateFitValues();
        calculateCumFitValues();
    }

    private void calculateTargetVals(Function<Individual> F)
    {
        targetVals = new double[N];
        feasible = new boolean[N];
        for (int i = 0; i < N; ++i)
        {
            targetVals[i] = F.value(inds.get(i));
            feasible[i] = F.isLastFisible();
        }
    }

    private void calculateFitValues()
    {
        fitVals = new double[N];
        double sum = 0;
        for (int i = 0; i < N; ++i)
            sum += (targetVals[i] - minTargetVal);
        if (Math.abs(sum) < 0.000001)
        {
            for (int i = 0; i < N; ++i)
                fitVals[i] = 1.0/N;
        }
        else
        {
            for (int i = 0; i < N; ++i)
                fitVals[i] = (targetVals[i] - minTargetVal) / sum;
        }
    }

    private void calculateCumFitValues()
    {
        cumFitVals = new double[N];
        cumFitVals[0] = fitVals[0];
        for (int i = 1; i < N; ++i)
            cumFitVals[i] = cumFitVals[i-1] + fitVals[i];
    }
    
    @Override
    public boolean terminationCondition()
    {
        /*if (Math.abs(minTargetVal - maxTargetVal) < 0.00001)
            return rand.nextDouble() < 0.05;
        else*/
            return false;
    }
    
    public Individual randomIndividual(double r)
    {
        int i = firstGreaterThan(0, N-1, r);
        return inds.get(i);
    }
    
    private int firstGreaterThan(int beg, int end, double r)
    {
        if (end - beg < 8)
            return firstGreaterThanIterative(beg, end, r);
        int mid = beg + (end - beg) / 2;
        if (cumFitVals[mid] >= r)
            return firstGreaterThan(beg, mid, r);
        else
            return firstGreaterThan(mid+1, end, r);
    }

    private int firstGreaterThanIterative(int beg, int end, double r)
    {
        for (int i = beg; i <= end; ++i)
            if (cumFitVals[i] >= r)
                return i;
        throw new RuntimeException("Flow should not reach that statement");
    }
    
    public void addIndividual(Individual individual)
    {
        inds.add(individual);
    }

    private void calculateStats()
    {
        minTargetVal = Double.POSITIVE_INFINITY;
        maxTargetVal = Double.NEGATIVE_INFINITY;
        meanTargetVal = 0;
        for (int i = 0; i < N; ++i)
        {
            if (!feasible[i])
                continue;
            if (targetVals[i] < minTargetVal)
            {
                minTargetVal = targetVals[i];
                minIndividual = inds.get(i);
            }
            if (targetVals[i] > maxTargetVal)
            {
                maxTargetVal = targetVals[i];
                maxIndividual = inds.get(i);
            }
            meanTargetVal += targetVals[i] / N;
        }
    }

    @Override
    public double getMaxValue()
    {
        return maxTargetVal;
    }

    @Override
    public double getMinValue()
    {
        return minTargetVal;
    }

    @Override
    public double getMeanValue()
    {
        return meanTargetVal;
    }

    @Override
    public Individual getMaxIndividual()
    {
        return maxIndividual;
    }
    
    @Override
    public Individual getMinIndividual()
    {
        return minIndividual;
    }

    @Override
    public int getSize()
    {
        return inds.size();
    }

    @Override
    public Individual getIndividual(int i)
    {
        return inds.get(i);
    }

    @Override
    public ArrayList<ValuedIndividual<Individual>> createListOfSortedIndividuals()
    {
        ArrayList<ValuedIndividual<Individual>> list = new ArrayList<>();
        for (int i = 0; i < N; ++i)
        {
            list.add( new ValuedIndividual<>(inds.get(i), targetVals[i], feasible[i]) );
        }
        list.sort( (ValuedIndividual<Individual> o1, ValuedIndividual<Individual> o2)
                    -> -Double.compare(o1.value, o2.value) );
        return list;
    }
}
