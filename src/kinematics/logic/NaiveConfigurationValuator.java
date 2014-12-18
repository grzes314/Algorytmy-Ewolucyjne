
package kinematics.logic;

import sga.Function;

/**
 *
 * @author Grzegorz Los
 */
public class NaiveConfigurationValuator implements Function<Configuration>
{

    public NaiveConfigurationValuator(ProblemData problemData)
    {
        this.problemData = problemData;
        arm = new Arm(problemData.sData);
    }
    
    @Override
    public double value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        return last.distance(problemData.goal);
    }

    private final ProblemData problemData;
    private final Arm arm;
}
