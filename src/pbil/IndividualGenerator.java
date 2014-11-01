
package pbil;

/**
 *
 * @author grzes
 */
public interface IndividualGenerator
{
    Individual generate();    
    void improve(Individual ind, double learnRate);
    void reset();
    public void mutate(double mutationProb, double mutationRate);
}
