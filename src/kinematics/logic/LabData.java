
package kinematics.logic;

import java.io.Serializable;
import kinematics.logic.Utils.LocInCell;

/**
 *
 * @author Grzegorz Los
 */
public class LabData implements Serializable
{
    public final int rows, cols;
    public final Field[][] fields;
    public final Point minArea, maxArea;
    private final double fw, fh;
    
    public LabData(int rows, int cols, double edgeSize)
    {
        this.rows = rows;
        this.cols = cols;
        fields = new Field[rows][cols];
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                fields[r][c] = Field.Empty;
        fw = edgeSize;
        fh = edgeSize;
        minArea = new Point(- cols * edgeSize / 2.0, -10.0);
        maxArea = new Point(cols * edgeSize / 2.0, rows * edgeSize - 10.0);
    }
    
    public LabData(int rows, int cols, Point minArea, Point maxArea)
    {
        this.rows = rows;
        this.cols = cols;
        this.minArea = minArea;
        this.maxArea = maxArea;
        fields = new Field[rows][cols];
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                fields[r][c] = Field.Empty;
        fw = (maxArea.x - minArea.x) / cols;
        fh = (maxArea.y - minArea.y) / rows;
    }

    ObstacleData toObstacleData()
    {
        //TODO improve that method
        int k = countWalls();
        Point[] from = new Point[k];
        Point[] to = new Point[k];
        double[] h = new double[k];
        double[] w = new double[k];
        double[] v = new double[k];
        int i = 0;
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                if (fields[r][c] == Field.Wall)
                {
                    from[i] = toPoint(r, c, LocInCell.Middle);
                    to[i] = new Point(0,0);
                    h[i] = fh;
                    w[i] = fw;
                    v[i] = 0;
                    i++;
                }
        return new ObstacleData(k, from, to, h, w, v);
    }

    private int countWalls()
    {
        int count = 0;
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                if (fields[r][c] == Field.Wall)
                    count++;
        return count;
    }

    private Point toLowerLeft(int r, int c)
    {
        double x = c * fw + minArea.x;
        double y = (rows - 1 - r) * fh + minArea.y;
        return new Point(x, y);
    }

    public Point toPoint(int row, int col, LocInCell lic)
    {
        Point p = toLowerLeft(row, col);
        switch(lic)
        {
            case UpperLeft:
                return new Point(p.x, p.y + fh);
            case UpperRight:
                return new Point(p.x + fw, p.y + fh);
            case LowerLeft:
                return p;
            case LowerRight:
                return new Point(p.x + fw, p.y);                
            case Middle:
                return new Point(p.x + 0.5*fw, p.y + 0.5*fh);
            default:
                throw new RuntimeException("Unsupported localization");
        }
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
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                if (fields[r][c] == Field.Goal)
                    return toPoint(r,c, LocInCell.Middle);
        throw new InvalidDataException("Goal not set");
    }
}
