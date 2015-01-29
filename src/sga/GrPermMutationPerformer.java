
package sga;

import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class GrPermMutationPerformer implements MutationPerformer<GrPerm>
{

    @Override
    public Population<GrPerm> mutation(Population<GrPerm> population, double thetaM)
    {
        SimplePopulation<GrPerm> mutated = new SimplePopulation<>();
        int N = population.getSize();
        for (int i = 0; i < N; ++i)
        {
            mutated.addIndividual( mutate(population.getIndividual(i).ind, thetaM) );
        }
        return mutated;
    }

    private GrPerm mutate(GrPerm individual, double thetaM)
    {
        if (rand.nextDouble() < thetaM)
        {
            int[] sepCode = individual.toSeperatorCode();
            int i = rand.nextInt(sepCode.length);
            int j = rand.nextInt(sepCode.length);
            int pom = sepCode[i];
            sepCode[i] = sepCode[j];
            sepCode[j] = pom;
            GrPerm perm = new GrPerm(individual.getNrOfObjects(), individual.getNrOfGroups());
            perm.readSeperatorCode(sepCode);
            return perm;
        }
        else return individual;
    }

    @Override
    public void reset()
    {
    }

}
