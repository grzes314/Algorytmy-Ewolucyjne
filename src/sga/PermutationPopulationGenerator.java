
package sga;

import static sga.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class PermutationPopulationGenerator implements RandomPopulationGenerator<Permutation>
{
    private final int k;
    private final int n;

    public PermutationPopulationGenerator(int nrOfObjects, int nrOfGroups)
    {
        this.n = nrOfObjects;
        this.k = nrOfGroups;
    }
    
    @Override
    public Population<Permutation> generate(int N)
    {
        SimplePopulation<Permutation> pop = new SimplePopulation<>();
        for (int i = 0; i < N; ++i)
        {
            Permutation ind = generateInd();
            pop.addIndividual(ind);
        }
        return pop;
    }

    private Permutation generateInd()
    {
        int[] perm = getRandomPerm(n + k - 1);
        Permutation ind = new Permutation(n, k);
        ind.readSeperatorCode(perm);
        return ind;
    }
    
    private int[] getRandomPerm(int m)
    {      
        int[] perm = new int[m];
        for (int i = 0; i < m; i++) {
            int j = rand.nextInt(i+1);
            perm[i] = perm[j];
            perm[j] = i;
        }
        return perm;
    }

}
