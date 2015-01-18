
package optimization;

import java.util.ArrayList;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class Results<Individual>
{
    private ArrayList<ValuedIndividual<Individual>> results = new ArrayList<>();
    private ArrayList<Double> times = new ArrayList<>();
    private Individual bestInd;
    private double best, mean, worst, avgTime;
    
    public void addResult(ValuedIndividual<Individual> result, double time)
    {
        results.add(result);
        times.add(time);
    }
    
    public ValuedIndividual<Individual> getResult(int i)
    {
        return results.get(i);
    }
    
    public void calcStats()
    {
        best = Double.NEGATIVE_INFINITY;
        worst = Double.POSITIVE_INFINITY;
        mean = 0.0;
        int n = results.size();
        for (ValuedIndividual<Individual> vi: results)
        {
            if (vi.value > best)
            {
                best = vi.value;
                bestInd = vi.ind;
            }
            if (vi.value < worst)
                worst = vi.value;
            mean += vi.value / n;
        }
        
        avgTime = 0.0;
        for (Double t: times)
            avgTime += t / n;
    }

    public Individual getBestInd()
    {
        return bestInd;
    }

    public double getBest()
    {
        return best;
    }

    public double getMean()
    {
        return mean;
    }

    public double getWorst()
    {
        return worst;
    }

    public double getAvgTime()
    {
        return avgTime;
    }
    
    
}
