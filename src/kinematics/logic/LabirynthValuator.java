
package kinematics.logic;

import optimization.Function;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class LabirynthValuator implements Function<Configuration>
{
    private final PrDataForLab pData;
    private final Labirynth lab;
    private final Arm arm;

    public LabirynthValuator(PrDataForLab pData, Labirynth lab)
    {
        this.pData = pData;
        this.lab = lab;
        arm = new Arm(pData.armData);
    }
    
    @Override
    public ValuedIndividual<Configuration> value(Configuration x)
    {
        arm.setConfiguration(x);
        Point last = arm.getLastCoord();
        double infeasibility = lab.getIntersectionLength(arm);
        double d1 = lab.getEucDistToGoal(last);
        double d2 = lab.getPerimDistToGoal(last);
        return new ValuedIndividual<>(x, -(d1 + d2), infeasibility < 1e-6, infeasibility); 
    }

}
