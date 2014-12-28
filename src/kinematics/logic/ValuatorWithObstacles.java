
package kinematics.logic;

import sga.Function;

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
    public double value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        return -last.distance(problemData.goal);
    }
    
    @Override
    public boolean isLastFisible()
    {
        return !board.intersects(arm.getLines());
    }

    private final ProblemData problemData;
    private final Arm arm;
    private final Board board;
}
