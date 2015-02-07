
package kinematics.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grzegorz Los
 */
public class LabPiece
{
    private final Point[] points;
    private final Line[] lines;
    private final double[] fromP0ToP;
        
    public LabPiece(List<Point> points)
    {
        int n = points.size();
        this.points = new Point[n];
        lines = new Line[n];
        fromP0ToP = new double[n];
        int i = 0;
        for (Point p: points)
        {
            this.points[i] = p;
            i++;
        }
        initLines();
        initDist();
    }

    private void initLines()
    {
        int n = points.length;
        for (int i = 0; i < n; ++i)
        {
            int j = (i + 1) % n;
            lines[i] = new Line(points[i], points[j]);
        }
    }
    
    private void initDist()
    {
        int n = points.length;
        fromP0ToP[0] = 0.0;
        for (int i = 0; i < n; ++i)
            fromP0ToP[(i+1)%n] =  fromP0ToP[i] + lines[i].getLength();        
    }
    
    public double perimDist(Point from, Point to)
    {
        Line line = new Line(from, to);
        List<Intersection> ints = getIntersections(line);
        
        if (ints.size() == 0)
            return 0.0;
        else if (ints.size() == 1)
            throw new RuntimeException("How on earth?");
        
        Intersection fr = getClosest(ints, from);
        Intersection too = getClosest(ints, to);
        
        double d = getDist(fr.lineInd, too.lineInd);
        double offsetFrom = points[fr.lineInd].distance(fr.p);
        double offsetTo = points[too.lineInd].distance(too.p);
        d = d - offsetFrom + offsetTo;
        return Math.min(d, fromP0ToP[0] - d);
    }

    private List<Intersection> getIntersections(Line line)
    {
        List<Intersection> res = new ArrayList<>();
        for (int i = 0; i < lines.length; ++i)
        {
            Point p = lines[i].getItersection(line);
            if (p != null)
                res.add(new Intersection(p, i));
        }
        return res;
    }

    private Intersection getClosest(List<Intersection> ints, Point p)
    {
        Intersection res = null;
        double currVal = Double.POSITIVE_INFINITY;
        for (Intersection in: ints)
        {
            double d = in.p.distance(p);
            if (d < currVal)
            {
                res = in;
                currVal = d;
            }
        }
        return res;
    }

    private double getDist(int lineInd, int lineInd0)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public class Intersection
    {
        public final Point p;
        public final int lineInd;

        public Intersection(Point p, int lineInd)
        {
            this.p = p;
            this.lineInd = lineInd;
        }
    }
    
}
