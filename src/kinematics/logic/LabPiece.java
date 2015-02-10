
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
    private final double perim;
    private Point lastTo;
    private int closestToTo;
        
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
        perim = fromP0ToP[n-1] + lines[n-1].getLength();
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
        for (int i = 1; i < n; ++i)
            fromP0ToP[i] =  fromP0ToP[i-1] + lines[i-1].getLength();        
    }
    
    /*public double perimDist(Point from, Point to)
    {        
        Line line = new Line(from, to);
        List<Intersection> ints = getIntersections(line);
        
        if (ints.isEmpty())
            return 0.0;
        else if (ints.size() % 2 == 1) //ints.size() == 1 when we are inside piece
            return perim/2;
        
        Intersection fr = getClosest(ints, from);
        Intersection too = getClosest(ints, to);
        
        double d = getDist(fr.lineInd, too.lineInd);
        double offsetFrom = points[fr.lineInd].distance(fr.p);
        double offsetTo = points[too.lineInd].distance(too.p);
        if (fr.lineInd <= too.lineInd)
          d = d - offsetFrom + offsetTo;
        else
            d = d + offsetFrom - offsetTo;
        return Math.min(d, perim - d);
    }*/
    
    /*public double perimDist(Point from, Point to)
    {
        findClosestTo(to);
        
        Line line = new Line(from, to);
        return perimDist(line);
    }*/
    
    public double perimDist(Point from, Point to)
    {
        findClosestTo(to);
        
        Line line1 = new Line(from, new Point(from.x, 1e4));
        Line line2 = new Line(from, new Point(from.x, -1e4));
        Line line3 = new Line(from, new Point(-1e4, from.y));
        Line line4 = new Line(from, new Point(1e4, from.y));
        double d[] = new double[4];
        d[0] = perimDist(line1);
        d[1] = perimDist(line2);
        d[2] = perimDist(line3);
        d[3] = perimDist(line4);
        for (int i = 0; i < 4; ++i)
            d[i] = d[i] < 1e-3 ? Double.POSITIVE_INFINITY : d[i];
        double res = perimDist(new Line(from, to));
        for (int i = 0; i < 4; ++i)
            if (d[i] < res)
                res = d[i];
        return res;
    }
    
    // Wyznacza odleglosc po obwodzie od pierwszego przeciecia wyznaczonego przez line do closesestToTo
    private double perimDist(Line line)
    {
        List<Intersection> ints = getIntersections(line);
        
        if (ints.isEmpty())
            return 0.0;
        else if (ints.size() % 2 == 1) //ints.size() == 1 when we are inside piece
            return perim/2;
        
        Intersection fr = getClosest(ints, line.p);
        
        double offset = points[fr.lineInd].distance(fr.p);
        double d = getDist(fr.lineInd, closestToTo);
        double d1 = d - offset;
        double d2 = (perim - d) + offset;
        return Math.min(d1, d2);
    }
    
    

    public List<Intersection> getIntersections(Line line)
    {
        List<Intersection> res = new ArrayList<>();
        for (int i = 0; i < lines.length; ++i)
        {
            Point p = lines[i].getItersection(line);
            if (p != null)
            {
                //if (lines[i].p.distance(p) > 1.0e-3 && lines[i].r.distance(p) > 1.0e-3) //dont add intersections in corners
                    res.add(new Intersection(p, i));
            }
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

    private double getDist(int pointNr1, int pointNr2)
    {
        if (pointNr1 <= pointNr2)
            return fromP0ToP[pointNr2] - fromP0ToP[pointNr1];
        else
            return perim + fromP0ToP[pointNr2] - fromP0ToP[pointNr1];
    }

    public Point[] getPoints()
    {
        return points;
    }

    public Line[] getLines()
    {
        return lines;
    }

    private void findClosestTo(Point to)
    {
        // closestToTo = to; // <----- how it previously worked
        if (to != lastTo)
        {
            closestToTo = 0;
            double dist = to.distance(points[0]);
            for (int i = 1; i < points.length; ++i)
            {
                double newD = to.distance(points[i]);
                if (newD < dist)
                {
                    dist = newD;
                    closestToTo = i;
                }
            }
            lastTo = to;
        }
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
