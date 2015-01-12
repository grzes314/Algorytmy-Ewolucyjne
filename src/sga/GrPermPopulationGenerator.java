
package sga;

import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class GrPermPopulationGenerator implements RandomPopulationGenerator<GrPerm>
{
    private final int k;
    private final int n;

    public GrPermPopulationGenerator(int nrOfObjects, int nrOfGroups)
    {
        this.n = nrOfObjects;
        this.k = nrOfGroups;
    }
    
    @Override
    public Population<GrPerm> generate(int N)
    {
        SimplePopulation<GrPerm> pop = new SimplePopulation<>();
        for (int i = 0; i < N; ++i)
        {
            GrPerm ind = generateInd();
            pop.addIndividual(ind);
        }
        return pop;
    }

    private GrPerm generateInd()
    {
        int[] perm = getRandomPerm(n + k - 1);
        GrPerm ind = new GrPerm(n, k);
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
