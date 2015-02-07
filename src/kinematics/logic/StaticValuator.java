
package kinematics.logic;

import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class StaticValuator implements Function<Configuration>
{

    public StaticValuator(PrDataForDynamic problemData, Board board)
    {
        this.problemData = problemData;
        this.board = board;
        arm = new Arm(problemData.armData);
    }
    
    @Override
    public ValuedIndividual<Configuration> value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        double infeasibility = board.getTotalIntersectionLength(arm.getLines());
        return new ValuedIndividual<>(x, -last.distance(problemData.goal), infeasibility < 1e-6, infeasibility);
    }

    private final PrDataForDynamic problemData;
    private final Arm arm;
    private final Board board;
}
