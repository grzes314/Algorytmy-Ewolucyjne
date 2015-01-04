
package kinematics.logic;

import sga.Function;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class DynamicFunction<ArgType> implements Function<ArgType>
{
    private Function<ArgType> fun;

    public DynamicFunction(Function<ArgType> fun)
    {
        this.fun = fun;
    }
    
    @Override
    public ValuedIndividual<ArgType> value(ArgType x)
    {
        return fun.value(x);
    }

    public void update(Function<ArgType> fun)
    {
        this.fun = fun;
    }
}
