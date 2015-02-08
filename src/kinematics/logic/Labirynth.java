
package kinematics.logic;

import java.util.ArrayList;
import java.util.List;
import kinematics.logic.LabPiece.Intersection;

/**
 *
 * @author Grzegorz Los
 */
public class Labirynth
{
    public final ArrayList<LabPiece> pieces = new ArrayList<>();
    public final Point goal;

    public Labirynth(Point goal)
    {
        this.goal = goal;
    }
    
    public double getEucDistToGoal(Point from) {
        return from.distance(goal);
    }
    
    public double getPerimDistToGoal(Point from)
    {
        double sum = 0.0;
        for (LabPiece lp: pieces)
            sum += lp.perimDist(from, goal);
        return sum;
    }
    
    public boolean intersects(Line[] lines)
    {
        for (Line line: lines)
        {
            for (LabPiece lp: pieces)
            {
                if (!lp.getIntersections(line).isEmpty())
                    return true;
            }            
        }
        return false;
    }
    
    public double getIntersectionLength(Arm arm)
    {
        Line[] lines = arm.getLines();
        boolean inside = false;
        double sum = 0.0;
        for (int i = 0; i < lines.length; ++i)
        {
            ArrayList<Point> ps = divideInIntervals(lines[i]);
            double d = sumOfEverySecondInterval(ps);
            sum += inside ? d : (lines[i].getLength() - d);
            if (ps.size() % 2 == 1)
                inside = !inside;
        }
        return sum;
    }

    private ArrayList<Point> divideInIntervals(Line line)
    {
        ArrayList<Point> ps = new ArrayList<>();
        ps.add(line.p);
        for (LabPiece lp: pieces)
        {
            List<Intersection> ints = lp.getIntersections(line);
            for (Intersection in: ints)
                ps.add(in.p);
        }
        ps.add(line.r);
        ps.sort((Point p1, Point p2) -> {
                return Double.compare(p1.distance(line.p), p2.distance(line.p));
            });   
        return ps;
    }

    private double sumOfEverySecondInterval(ArrayList<Point> ps)
    {
        double sum = 0.0;
        for (int i = 0; i < ps.size()/2; ++i)
        {
            Point p1 = ps.get(2*i);
            Point p2 = ps.get(2*i+1);
            sum += p1.distance(p2);
        }
        return sum;
    }
}
