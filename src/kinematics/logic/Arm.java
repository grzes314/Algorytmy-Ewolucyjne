
package kinematics.logic;

import kinematics.logic.ProblemData.SegmentData;

/**
 *
 * @author Grzegorz Los
 */
public class Arm
{
    private final SegmentData segmentData;
    private Point coord[];
    private Configuration conf;

    public Arm(SegmentData segmentData)
    {
        this.segmentData = segmentData;
    }
    
    public void setConfiguration(Configuration conf)
    {
        this.conf = conf;
        if (conf != null)
            calculateCoords();
    }

    private void calculateCoords()
    {
        int n = conf.angle.length;
        coord = new Point[n];
        Vector u = new Vector(0,-1);
        Point prev = new Point(0,0);
        for (int i = 0; i < n; ++i)
        {
            double a = conf.angle[i];
            Vector v = u.rotate(a);
            v.scaleTo(segmentData.length[i]);
            coord[i] = prev.plus(v);
            
            u = new Vector(coord[i], prev);
            prev = coord[i];
        }
    }

    public Point[] getCoord()
    {
        return coord;
    }

    public Point getLastCoord()
    {
        return coord[segmentData.n-1];
    }
}
