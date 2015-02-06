
package kinematics.gui.labcreator;

import java.util.ArrayList;
import kinematics.logic.ObstacleData;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;
import kinematics.logic.Rectangle;

/**
 *
 * @author Grzegorz Los
 */
public class LabDataWithRects
{
    public final Point minArea;
    public final Point maxArea;
    private Point goal;
    private ArrayList<Rectangle> rects = new ArrayList<>();

    public LabDataWithRects(Point minArea, Point maxArea)
    {
        this.minArea = minArea;
        this.maxArea = maxArea;
    }
    
    public void addRectangle(double leftBottomX, double leftBottomY, double rightUpperX, double rightUpperY)
    {
        double w = rightUpperX - leftBottomX;
        double h = rightUpperY - leftBottomY;
        double x = (rightUpperX + leftBottomX) / 2;
        double y = (rightUpperY + leftBottomY) / 2;
        Rectangle rect = new Rectangle(new Point(x, y), new Point(0,0), 0.0, h, w);
        rects.add(rect);
    }
    
    public void removeLast()
    {
        int i = rects.size() - 1;
        if (i >= 0)
            rects.remove(i);
    }
    
    public void setGoal(double x, double y)
    {
        goal = new Point(x, y);
    }

    ObstacleData toObstacleData()
    {
        int k = rects.size();
        Point[] from = new Point[k];
        Point[] to = new Point[k];
        double[] h = new double[k];
        double[] w = new double[k];
        double[] v = new double[k];
        int i = 0;
        for (Rectangle rect: rects)
        {
            from[i] = rect.from;
            to[i] = rect.to;
            h[i] = rect.h;
            w[i] = rect.w;
            v[i] = rect.vel;
            i++;
        }
        return new ObstacleData(k, from, to, h, w, v);
    }

    public Point getAreaMinPoint()
    {
        return minArea;
    }

    public Point getAreaMaxPoint()
    {
        return maxArea;
    }
    
    public Point getGoal() throws InvalidDataException
    {
        if (goal != null)
            return goal;
        else
            throw new InvalidDataException("Goal wasn't set");
    }

    public ArrayList<Rectangle> getRects()
    {
        return rects;
    }
}
