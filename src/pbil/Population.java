
package pbil;

/**
 *
 * @author grzes
 */
public interface Population
{

    public void evaluate(TargetFunction fun);

    public boolean terminationConditionSatisfied();

    public Individual getBestIndividual();
    
}
