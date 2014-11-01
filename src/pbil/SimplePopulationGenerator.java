
package pbil;

/**
 *
 * @author grzes
 */
public class SimplePopulationGenerator implements PopulationGenerator
{
    public SimplePopulationGenerator(IndividualGenerator indGen)
    {
        this.indGen = indGen;
    }
    
    @Override
    public SimplePopulation generate(int popSize)
    {
        SimplePopulation pop = new SimplePopulation();
        for (int i = 0; i < popSize; ++i)
            pop.addIndividual( indGen.generate() );
        return pop;
    }

    @Override
    public void createInitialDistrs()
    {
        indGen.reset();
    }

    @Override
    public void improve(Individual ind, double learnRate)
    {
        indGen.improve(ind, learnRate);
    }

    @Override
    public void mutate(double mutationProb, double mutationRate)
    {
        indGen.mutate(mutationProb, mutationRate);
    }
    
    IndividualGenerator indGen;   
}
