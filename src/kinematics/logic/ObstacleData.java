
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class ObstacleData
{
    public final int k;
    public final Point[] from;
    public final Point[] to;
    public final double[] h;
    public final double[] w;
    public final double[] v;

    public ObstacleData(int k, Point[] from, Point[] to, double[] h, double[] w, double[] v)
    {
        this.k = k;
        this.from = from;
        this.to = to;
        this.h = h;
        this.w = w;
        this.v = v;
    }

}
