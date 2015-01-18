
package sga;

import optimization.Permutation;
import static optimization.RandomnessSource.rand;
import simplealgs.TranspositionChooser;

/**
 *
 * @author Grzegorz Los
 */
public class PermutationMutationPerformer implements MutationPerformer<Permutation>
{
    private final TranspositionChooser neighbourChooser;

    public PermutationMutationPerformer(TranspositionChooser neighbourChooser)
    {
        this.neighbourChooser = neighbourChooser;
    }
    
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

    private Permutation mutate(Permutation individual, double thetaM)
    {
        if (rand.nextDouble() < thetaM)
        {
            return neighbourChooser.chooseOne(individual);
        }
        else return individual;
    }

    @Override
    public void reset()
    {
    }

}
