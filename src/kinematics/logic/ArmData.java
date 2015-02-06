
package kinematics.logic;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class ArmData
{
    public final ArrayList<OneSegment> segments = new ArrayList<>();
    
    public ArmData() {
        
    }

    public ArmData(double[] length, double[] alfa, double[] beta)
    {
        int n = length.length;
        for (int i = 0; i < n; ++i)
            segments.add( new OneSegment(length[i], alfa[i], beta[i]) );        
    }

    public boolean add(OneSegment e)
    {
        return segments.add(e);
    }

    public int getSize() {
        return segments.size();
    }

    public OneSegment get(int index)
    {
        return segments.get(index);
    }
}
