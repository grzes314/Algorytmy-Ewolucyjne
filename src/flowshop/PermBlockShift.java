
package flowshop;

import optimization.Permutation;
import optimization.RandomnessSource;
import optimization.ValuedIndividual;
import sga.MutationPerformer;
import sga.Population;
import sga.SimplePopulation;

/**
 *
 * @author Grzegorz Los
 */
public class PermBlockShift implements MutationPerformer<Permutation>
{

    @Override
    public Population<Permutation> mutation(Population<Permutation> population, double thetaM)
    {
        SimplePopulation<Permutation> mutated = new SimplePopulation<>();
        int N = population.getSize();
        for (int i = 0; i < N; ++i)
        {
            mutated.addIndividual( mutate(population.getIndividual(i), thetaM) );
        }
        return mutated;    
    }

    private ValuedIndividual<Permutation> mutate(ValuedIndividual<Permutation> individual, double thetaM)
    {
        if (thetaM < RandomnessSource.rand.nextDouble())
            return new ValuedIndividual<>(reallyMutate(individual.ind));
        else
            return individual;
    }

    private Permutation reallyMutate(Permutation individual)
    {
        int n = individual.getSize();
        int a = RandomnessSource.rand.nextInt(n);
        int b = RandomnessSource.rand.nextInt(n);
        if (b < a)
        {
            int pom = b;
            b = a;
            a = pom;
        }
        int k = RandomnessSource.rand.nextInt(n - (b-a));
        return shift(individual, n, a, b, k);
    }

    private Permutation shift(Permutation p, int n, int a, int b, int k)
    {
        if (k < a)
            return shiftWhenKsmallerA(p, n, a, b, k);
        else
            return shiftWhenKgreaterEqA(p, n, a, b, k);
    }

    private Permutation shiftWhenKsmallerA(Permutation p, int n, int a, int b, int k)
    {
        int[] r = new int[n];
        int l = b - a + 1;
        for (int i = 0; i < n; ++i)
        {
            if (i < k)
                r[i] = p.at(i);
            else if (i < k + l)
                r[i] = p.at(a+i-k);
            else if (i < a + l)
                r[i] = p.at(i-l);
            else
                r[i] = p.at(i);
        }
        return new Permutation(r);
    }

    private Permutation shiftWhenKgreaterEqA(Permutation p, int n, int a, int b, int k)
    {
        int[] r = new int[n];
        int l = b - a + 1;
        for (int i = 0; i < n; ++i)
        {
            try {
            if (i < a)
                r[i] = p.at(i);
            else if (i < k)
                r[i] = p.at(i+l);
            else if (i < k + l)
                r[i] = p.at(i + a - k);
            else
                r[i] = p.at(i);
                
            } catch (Exception ex) {
                int dfsg = 9;
            }
        }
        return new Permutation(r);
    }

    @Override
    public void reset()
    {
    }

}
