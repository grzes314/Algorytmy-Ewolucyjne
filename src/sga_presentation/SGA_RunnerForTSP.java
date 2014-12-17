
package sga_presentation;

import sga.CrossoverPerformer;
import sga.MutationPerformer;
import sga.PMX;
import sga.ParentSelector;
import sga.PermutationMutationPerformer;
import sga.PermutationPopulationGenerator;
import sga.RandomPopulationGenerator;
import sga.ReplacementPerformer;
import sga.RouletteParentSelector;
import sga.SimpleReplacementPerformer;

/**
 *
 * @author Grzegorz Los
 */
public class SGA_RunnerForTSP extends SGA_Runner
{
    private final int nrOfObjects;
    private final int nrOfGroups;

    public SGA_RunnerForTSP(int nrOfObjects, int nrOfGroups)
    {
        this.nrOfObjects = nrOfObjects;
        this.nrOfGroups = nrOfGroups;
    }

    @Override
    protected RandomPopulationGenerator makeRandomPopulationGenerator()
    {
        return new PermutationPopulationGenerator(nrOfObjects, nrOfGroups);
    }

    @Override
    protected ParentSelector makeParentSelector()
    {
        return new RouletteParentSelector();
    }

    @Override
    protected CrossoverPerformer makeCrossoverPerformer()
    {
        return new PMX();
    }

    @Override
    protected MutationPerformer makeMutationPerformer()
    {
        return new PermutationMutationPerformer();
    }

    @Override
    protected ReplacementPerformer makeReplacementPerformer()
    {
        return new SimpleReplacementPerformer();
    }

}
