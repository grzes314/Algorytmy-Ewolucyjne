
package sga;

import optimization.BitString;
import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class BitStringMutationPerformer implements MutationPerformer<BitString>
{

    @Override
    public Population<BitString> mutation(Population<BitString> population, double thetaM)
    {
        SimplePopulation<BitString> mutated = new SimplePopulation<>();
        int N = population.getSize();
        for (int i = 0; i < N; ++i)
        {
            mutated.addIndividual( mutate(population.getIndividual(i), thetaM) );
        }
        return mutated;
    }

    private BitString mutate(BitString individual, double thetaM)
    {
        int l = individual.bits.length;
        BitString res = new BitString(l);
        for (int i = 0; i < l; ++i)
        {
            if (rand.nextDouble() < thetaM)
                res.bits[i] = !individual.bits[i];
            else
                res.bits[i] = individual.bits[i];
        }
        return res;
    }

    @Override
    public void reset()
    {
    }

}
