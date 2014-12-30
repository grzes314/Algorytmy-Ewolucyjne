
package kinematics.logic;

import sga.Function;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class ValuatorWithObstacles implements Function<Configuration>
{

    public ValuatorWithObstacles(ProblemData problemData, Board board)
    {
        this.problemData = problemData;
        this.board = board;
        arm = new Arm(problemData.sData);
    }
    
    @Override
    public ValuedIndividual<Configuration> value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        boolean feasible = !board.intersects(arm.getLines());
        return new ValuedIndividual<>(x, -last.distance(problemData.goal), feasible);
    }

    private final ProblemData problemData;
    private final Arm arm;
    private final Board board;
}
