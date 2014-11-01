
package pbil;

/**
 *
 * @author grzes
 */
public interface PopulationGenerator
{

    public void createInitialDistrs();

    public Population generate(int popSize);

    public void improve(Individual ind, double learnRate);

    public void mutate(double mutationProb, double mutationRate);
    
}
