
package kinematics.logic;

import java.util.ArrayList;
import java.util.List;
import kinematics.logic.ObstacleData;

/**
 *
 * @author Grzegorz Los
 */
public class Board
{
    public final Point minArea, maxArea;
    public final Point goal;
    private final List<Rectangle> rects = new ArrayList<>();
    
    public Board(ProblemData pData)
    {
        minArea = pData.minArea;
        maxArea = pData.maxArea;
        goal = pData.goal;
        ObstacleData oData = pData.oData;
        
        for (int i = 0; i < oData.k; ++i)
        {
            Rectangle rect = new Rectangle(oData.from[i], oData.to[i], oData.v[i], oData.h[i], oData.w[i]);
            rects.add(rect);
        }
    }
    
    public Board(Board board)
    {
        minArea = board.minArea;
        maxArea = board.maxArea;
        goal = board.goal;
        for (Rectangle rect: board.rects)
            rects.add(new Rectangle(rect));
    }
    
    public void move(double deltaTime)
    {
        for (Rectangle rect: rects)
            rect.move(deltaTime);
    }
    
    public boolean intersects(Line[] lines)
    {
        for (Line line: lines)
        {
            if (intersects(line))
                return true;
        }
        return false;
    }
    
    public boolean intersects(Line line)
    {
        for (Rectangle rect: rects)
        {
            if (rect.intersects(line))
                return true;
        }
        return false;
    }
    
    public double getTotalIntersectionLength(Line[] lines)
    {
        double sum = 0.0;
        for (Rectangle rect: rects)
            sum += rect.getIntersectionLength(lines);
        return sum;
    }

    public List<Rectangle> getRects()
    {
        return rects;
    }

    void merge(Board board)
    {
        for (Rectangle rect: board.getRects())
            rects.add(rect);
    }
}
