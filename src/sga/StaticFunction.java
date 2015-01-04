
package sga;

import kinematics.logic.DynamicFunction;

/**
 *
 * @author Grzegorz Los
 */
public class StaticFunction<ArgType> extends DynamicFunction<ArgType>
{

    public StaticFunction(Function<ArgType> fun)
    {
        super(fun);
    }
    
    @Override
    public void update(Function<ArgType> fun)
    {        
    }
}
