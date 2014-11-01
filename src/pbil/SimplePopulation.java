
package pbil;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class SimplePopulation implements Population
{
    public SimplePopulation() {
        individuals = new ArrayList<>();
    }
    
    public int getSize()
    {
        return individuals.size();
    }
    
    public Individual getIndividaul(int nr)
    {
        return individuals.get(nr);
    }
    
    public void addIndividual(Individual ind)
    {
        individuals.add(ind);
    }

    @Override
    public void evaluate(TargetFunction fun)
    {
        scores = new double[individuals.size()];
        int i = 0;
        for (Individual ind: individuals)
        {
            scores[i] = fun.value(ind);
            i++;
        }
    }

    @Override
    public boolean terminationConditionSatisfied()
    {
        return false;
    }

    @Override
    public Individual getBestIndividual()
    {
        int bestNr = 0;
        double bestScore = scores[0];
        for (int i = 1; i < getSize(); ++i)
        {
            if (scores[i] > bestScore)
            {
                bestNr = i;
                bestScore = scores[i];
            }
        }
        return individuals.get(bestNr);
    }
    
    private final ArrayList<Individual> individuals;
    private double[] scores;
}
