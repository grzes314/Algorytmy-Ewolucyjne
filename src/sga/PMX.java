
package sga;

import optimization.Permutation;
import optimization.RandomnessSource;
import static optimization.RandomnessSource.rand;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class PMX implements CrossoverPerformer<Permutation>
{
    SimplePopulation<Permutation> newPopulation;

    @Override
    public Population<Permutation> crossover(Population<Permutation> parents, double thetaC)
    {
        newPopulation = new SimplePopulation<>();
        int N = parents.getSize();
        if (N % 2 != 0)
            N--;
        for (int i = 0; i < N; i += 2)
        {
            ValuedIndividual<Permutation> p = parents.getIndividual(i);
            ValuedIndividual<Permutation> r = parents.getIndividual(i+1);
            if (rand.nextDouble() < thetaC && !p.ind.equals(r.ind))
                crossover(p.ind, r.ind);
            else
            {
                newPopulation.addIndividual(p);
                newPopulation.addIndividual(r);
            }
        }
        return newPopulation;
    }
    
    private void crossover(Permutation p1, Permutation p2)
    {
        int n = p1.getSize();
        int k = RandomnessSource.rand.nextInt(n);
        int l = RandomnessSource.rand.nextInt(n);
        if (k > l) {
            int aux = k;
            k = l;
            l = aux;
        }
        // k <= l
        crossover(p1, p2, k, l);
    }
    
    private void crossover(Permutation p1, Permutation p2, int k, int l)
    {
        Permutation r1 = replace(p1, p2, k, l);
        Permutation r2 = replace(p2, p1, k, l);
        newPopulation.addIndividual(r1);
        newPopulation.addIndividual(r2);
    }

    private Permutation replace(Permutation to, Permutation from, int k, int l)
    {
        Integer[] map = makeMap(to, from, k, l);
        int[] perm = new int[to.getSize()];
        for (int i = 0; i < k; ++i)
            perm[i] = findNewValue(to.at(i), map);
        for (int i = k; i <= l; ++i)
            perm[i] = from.at(i);
        for (int i = l+1; i < perm.length; ++i)
            perm[i] = findNewValue(to.at(i), map);
        return new Permutation(perm);
    }

    private Integer[] makeMap(Permutation to, Permutation from, int k, int l)
    {
        Integer[] map = new Integer[to.getSize()];
        for (int i = k; i <= l; ++i)
            map[from.at(i)] = to.at(i);
        return map;
    }

    private int findNewValue(int prev, Integer[] map)
    {
        int val = prev;
        Integer aux = map[val];
        while (aux != null)
        {
            val = aux;
            aux = map[val];
        }
        return val;
    }
}
