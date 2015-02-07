
package kinematics.logic;

import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class DynamicValuator implements Function<Configuration>
{
    private final PrDataForDynamic problemData;
    private StaticValuator valuator;

    public DynamicValuator(PrDataForDynamic problemData, Board initialBoard)
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
