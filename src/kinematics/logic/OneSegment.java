
package kinematics.logic;

import java.io.Serializable;

/**
 *
 * @author Grzegorz Los
 */
public class OneSegment implements Serializable
{
    public final double length, minAngle, maxAngle;

    public OneSegment(double length, double minAngle, double maxAngle)
    {
        this.length = length;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }
    
    @Override
    public String toString()
    {
        return length + "; " + minAngle + "; " + maxAngle;
    }
}
