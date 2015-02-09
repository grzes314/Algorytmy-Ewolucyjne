
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Arm
{
    private final ArmData armData;
    private Point coord[];
    private Line lines[];
    private double length;
    private Configuration conf;

    public Arm(ArmData armData)
    {
        this.armData = armData;
        calcLength();
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
            double a = conf.angle[i];
            Vector v = u.rotate(a);
            v.scaleTo(armData.get(i).length);
            newCoord[i] = prev.plus(v);
            
            u = new Vector(newCoord[i], prev);
            prev = newCoord[i];
        }
        coord = newCoord;
        calcLines();
    }

    public Point[] getCoord()
    {
        return coord;
    }
    
    public Line[] getLines()
    {
        return lines;
    }
    
    public void calcLines()
    {
        lines = new Line[coord.length];
        Point prev = new Point(0,0);
        for (int i = 0; i < lines.length; ++i)
        {
            lines[i] = new Line(prev, coord[i]);
            prev = coord[i];
        }
    }

    private void calcLength()
    {
        length = 0.0;
        for (OneSegment s: armData.segments)
            length += s.length;
    }
    public double getLength()
    {
        return length;
    }

    
    public Point getLastCoord()
    {
        return coord[armData.getSize()-1];
    }

    public Configuration getConf()
    {
        return conf;
    }
}
