
package kinematics.gui.labcreator;

import java.io.Serializable;
import java.util.ArrayList;
import kinematics.logic.ProblemData;

/**
 *
 * @author Grzegorz Los
 */
public class ArmData implements Serializable
{
    ArrayList<SegmentData> segments = new ArrayList<>();
    
    void addSegment(SegmentData segment)
    {
        segments.add(segment);
    }

    ProblemData.SegmentData toSegmentData()
    {
        int n = segments.size();
        double[] length = new double[n];
        double[] alfa = new double[n];
        double[] beta = new double[n];
        for (int i = 0; i < n; ++i)
        {
            SegmentData sd = segments.get(i);
            length[i] = sd.length;
            alfa[i] = sd.minAngle;
            beta[i] = sd.maxAngle;
        }
        return new ProblemData.SegmentData(n, length, alfa, beta);
    }
}
