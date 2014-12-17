
package sga;

import static sga.RandomnessSource.rand;

/**
 *
 * @author Grzegorz Los
 */
public class BitStringPopulationGenerator implements RandomPopulationGenerator<BitString>
{
    private final int stringSize;
    
    public BitStringPopulationGenerator(int stringSize)
    {
        this.stringSize = stringSize;
    }

    @Override
    public Population<BitString> generate(int N)
    {
        SimplePopulation<BitString> population = new SimplePopulation<>();
        for (int i = 0; i < N; ++i)
            population.addIndividual( generateBitString() );
        return population;
    }

    private BitString generateBitString()
    {
        BitString bitString = new BitString(stringSize);
        for (int i = 0; i < stringSize; ++i)
        {
            bitString.bits[i] = rand.nextInt(2) == 0;
        }
        return bitString;
    }

}
