
package sga;

import optimization.BitString;
import static optimization.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class BitStringCrossoverPerformer implements CrossoverPerformer<BitString>
{
    private SimplePopulation<BitString> newPopulation;
    
    @Override
    public Population<BitString> crossover(Population<BitString> parents, double thetaC)
    {
        newPopulation = new SimplePopulation<>();
        int N = parents.getSize();
        if (N % 2 != 0)
            throw new RuntimeException("Size of parent populations should be even");
        for (int i = 0; i < N; i += 2)
        {
            if (rand.nextDouble() < thetaC)
                crossover(parents.getIndividual(i), parents.getIndividual(i+1));
            else
            {
                newPopulation.addIndividual(parents.getIndividual(i));
                newPopulation.addIndividual(parents.getIndividual(i+1));
            }
        }
        return newPopulation;
    }
    
    private void crossover(BitString x, BitString y)
    {
        int l = x.bits.length;
        BitString c = new BitString(l), d = new BitString(l);
        int k = rand.nextInt(l);
        for (int i = 0; i < k; ++i)
        {
            c.bits[i] = x.bits[i];
            d.bits[i] = y.bits[i];
        }
        for (int i = k; i < l; ++i)
        {
            c.bits[i] = y.bits[i];
            d.bits[i] = x.bits[i];
        }
        newPopulation.addIndividual(c);
        newPopulation.addIndividual(d);
    }

}
