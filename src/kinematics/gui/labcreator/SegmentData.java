
package kinematics.gui.labcreator;

import java.io.Serializable;

/**
 *
 * @author Grzegorz Los
 */
public class SegmentData implements Serializable
{
    public final int length, minAngle, maxAngle;

    public SegmentData(int length, int minAngle, int maxAngle)
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
