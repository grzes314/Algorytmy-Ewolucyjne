
package kinematics.logic;

import java.util.ArrayList;
import java.util.List;
import kinematics.logic.ProblemData.ObstacleData;

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
            //public Rectangle(Point from, Point to, double v, double h, double w)
            Rectangle rect = new Rectangle(oData.from[i], oData.to[i], oData.v[i], oData.h[i], oData.w[i]);
            rects.add(rect);
        }
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
            Line[] rLines = rect.getLines();
            for (Line rLine: rLines)
                if (line.instersects(rLine))
                    return true;
        }
        return false;
    }

    public List<Rectangle> getRects()
    {
        return rects;
    }

}