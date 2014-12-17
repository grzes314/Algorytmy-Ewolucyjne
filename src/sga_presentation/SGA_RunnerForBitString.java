
package sga_presentation;

import sga.BitString;
import sga.BitStringCrossoverPerformer;
import sga.BitStringMutationPerformer;
import sga.BitStringPopulationGenerator;
import sga.CrossoverPerformer;
import sga.MutationPerformer;
import sga.ParentSelector;
import sga.RandomPopulationGenerator;
import sga.ReplacementPerformer;
import sga.RouletteParentSelector;
import sga.SimpleReplacementPerformer;

/**
 *
 * @author Grzegorz Los
 */
public class SGA_RunnerForBitString extends SGA_Runner<BitString>
{

    private final int stringSize;
    
    public SGA_RunnerForBitString(int stringSize)
    {
        this.stringSize = stringSize;
    }

    @Override
    protected RandomPopulationGenerator makeRandomPopulationGenerator()
    {
        return new BitStringPopulationGenerator(stringSize);
    }

    @Override
    protected ParentSelector makeParentSelector()
    {
        return new RouletteParentSelector();
    }

    @Override
    protected CrossoverPerformer makeCrossoverPerformer()
    {
        return new BitStringCrossoverPerformer();
    }

    @Override
    protected MutationPerformer makeMutationPerformer()
    {
        return new BitStringMutationPerformer();
    }

    @Override
    protected ReplacementPerformer makeReplacementPerformer()
    {
        return new SimpleReplacementPerformer<>();
    }

}
