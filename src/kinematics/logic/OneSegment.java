
package kinematics.logic;

import java.io.Serializable;
import static kinematics.logic.Utils.radToDeg;

/**
 *
 * @author Grzegorz Los
 */
public class OneSegment implements Serializable
{
    // Angles are in radians!!
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
        String alfa = String.format("%.2f", radToDeg(minAngle));
        String beta = String.format("%.2f", radToDeg(maxAngle));
        return length + "; " + alfa + "; " + beta;
    }
}
