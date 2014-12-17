
package sga;

import static sga.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class PermutationMutationPerformer implements MutationPerformer<Permutation>
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

    private Permutation mutate(Permutation individual, double thetaM)
    {
        if (rand.nextDouble() < thetaM)
        {
            int[] sepCode = individual.toSeperatorCode();
            int i = rand.nextInt(sepCode.length);
            int j = rand.nextInt(sepCode.length);
            int pom = sepCode[i];
            sepCode[i] = sepCode[j];
            sepCode[j] = pom;
            Permutation perm = new Permutation(individual.getNrOfObjects(), individual.getNrOfGroups());
            perm.readSeperatorCode(sepCode);
            return perm;
        }
        else return individual;
    }

}
