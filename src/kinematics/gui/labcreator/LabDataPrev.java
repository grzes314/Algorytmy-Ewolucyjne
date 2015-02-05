
package kinematics.gui.labcreator;

import java.io.Serializable;
import kinematics.logic.Point;
import kinematics.logic.ProblemData;

/**
 *
 * @author Grzegorz Los
 */
public class LabDataPrev implements Serializable
{
    public final int rows, cols;
    public final Field[][] fields;
    private final double fw = 20, fh = 20;
    
    public LabDataPrev(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        fields = new Field[rows][cols];
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                fields[r][c] = Field.Empty;
    }

    ProblemData.ObstacleData toObstacleData()
    {
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
                    from[i] = toPoint(r, c);
                    to[i] = new Point(0,0);
                    h[i] = fh;
                    w[i] = fw;
                    v[i] = 0;
                    i++;
                }
        return new ProblemData.ObstacleData(k, from, to, h, w, v);
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

    private Point toPoint(int r, int c)
    {
        double x = (c - cols/2.0) * fw;
        double y = (rows - r) * fh;
        return new Point(x, y);
    }

    public Point getAreaMinPoint()
    {
        return toPoint(rows, -1);
    }

    public Point getAreaMaxPoint()
    {
        return toPoint(-1, cols);
    }
    
    public Point getGoal() throws InvalidDataException
    {
        for (int r = 0; r < rows; ++r)
            for (int c = 0; c < cols; ++c)
                if (fields[r][c] == Field.Goal)
                    return toPoint(r,c);
        throw new InvalidDataException("Goal not set");
    }
}
