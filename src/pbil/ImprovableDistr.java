
package pbil;

/**
 *
 * @author Grzegorz Los
 */
public interface ImprovableDistr<Observation>
{
    public void improve(Observation obs, double learnRate);
    public Observation draw();
    public void mutate(double mutationProb, double mutationRate);
}
