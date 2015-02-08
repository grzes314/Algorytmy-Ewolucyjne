
package kinematics.logic;

import java.util.ArrayList;
import java.util.List;

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
                if (lp.getIntersections(line) != null)
                    return true;
            }            
        }
        return false;
    }
}
