
package sga;

import optimization.Permutation;
import optimization.RandomnessSource;
import static optimization.RandomnessSource.rand;
import simplealgs.NeighbourhoodChooser;

/**
 *
 * @author Grzegorz Los
 */
public class PermutationMutationPerformer implements MutationPerformer<Permutation>
{
    private final NeighbourhoodChooser<Permutation> neighbourChooser;
    private int maxTrans;
    private final Mode mode;

    public PermutationMutationPerformer(NeighbourhoodChooser<Permutation> neighbourChooser)
    {
        this(neighbourChooser, 1);
    }

    public PermutationMutationPerformer(NeighbourhoodChooser<Permutation> neighbourChooser, int maxTrans)
    {
        this(neighbourChooser, maxTrans, Mode.EXACT);
    }

    public PermutationMutationPerformer(NeighbourhoodChooser<Permutation> neighbourChooser, int maxTrans, Mode mode)
    {
        this.neighbourChooser = neighbourChooser;
        this.maxTrans = maxTrans;
        this.mode = mode;
    }
    
    @Override
    public Population<Permutation> mutation(Population<Permutation> population, double thetaM)
    {
        SimplePopulation<Permutation> mutated = new SimplePopulation<>();
        int N = population.getSize();
        for (int i = 0; i < N; ++i)
        {
            if (rand.nextDouble() < thetaM)
            {
                mutated.addIndividual( mutate(population.getIndividual(i).ind) );
            }
            else mutated.addIndividual( population.getIndividual(i) );
        }
        return mutated;
    }

    private Permutation mutate(Permutation individual)
    {
        int[] perm = individual.toArray();
        int k;
        switch(mode)
        {
            case EXACT:
                k = maxTrans;
                break;
            case UPTO:
                k = RandomnessSource.rand.nextInt(maxTrans) + 1;
                break;
            default:
                throw new RuntimeException("Impossible");
        }
        for (int xxx = 0; xxx < k; ++xxx)
        {
            int i = RandomnessSource.rand.nextInt(individual.getSize());
            int j = RandomnessSource.rand.nextInt(individual.getSize());
            int aux = perm[i];
            perm[i] = perm[j];
            perm[j] = aux;    
        }
        return new Permutation(perm);
    }

    @Override
    public void reset()
    {
    }

    public enum Mode {
        EXACT, UPTO
    }
}
