
package kinematics.logic;

import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class SimpleValuator implements Function<Configuration>
{

    public SimpleValuator(ProblemData problemData)
    {
        this.problemData = problemData;
        arm = new Arm(problemData.sData);
    }
    
    @Override
    public ValuedIndividual<Configuration> value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        return new ValuedIndividual<>(x, -last.distance(problemData.goal));
    }

    private final ProblemData problemData;
    private final Arm arm;
}
