
package kinematics.logic;

import kinematics.logic.ProblemData.SegmentData;
import kinematics.logic.Line;

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
        calculateCoords();
    }

    private void calculateCoords()
    {
        if (conf == null)
        {
            coord = null;
            return;
        }
        int n = conf.angle.length;
        Point[] newCoord = new Point[n];
        Vector u = new Vector(0,-1);
        Point prev = new Point(0,0);
        for (int i = 0; i < n; ++i)
        {
            double a = 1;
            try {
                a = conf.angle[i];
            } catch (Exception ex) {
                int b = 8;
            }
            Vector v = u.rotate(a);
            v.scaleTo(segmentData.length[i]);
            newCoord[i] = prev.plus(v);
            
            u = new Vector(newCoord[i], prev);
            prev = newCoord[i];
        }
        coord = newCoord;
    }

    public Point[] getCoord()
    {
        return coord;
    }
    
    public Line[] getLines()
    {
        if (coord == null)
            return null;
        Line[] lines = new Line[coord.length];
        Point prev = new Point(0,0);
        for (int i = 0; i < lines.length; ++i)
        {
            lines[i] = new Line(prev, coord[i]);
            prev = coord[i];
        }
        return lines;
    }

    public Point getLastCoord()
    {
        return coord[segmentData.n-1];
    }

    public Configuration getConf()
    {
        return conf;
    }
}
