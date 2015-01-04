
package kinematics.logic;

import sga.Function;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class DynamicValuator implements Function<Configuration>
{
    private final ProblemData problemData;
    private StaticValuator valuator;

    public DynamicValuator(ProblemData problemData, Board initialBoard)
    {
        this.problemData = problemData;
        valuator = new StaticValuator(problemData, initialBoard);
    }

    @Override
    public ValuedIndividual<Configuration> value(Configuration x)
    {
        return valuator.value(x);
    }
    
    public void updateBoard(Board newBoard)
    {
        valuator =  new StaticValuator(problemData, newBoard);
    }
}
