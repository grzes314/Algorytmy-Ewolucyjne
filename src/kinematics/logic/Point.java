
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Point
{

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public final double x,y;

    public Point plus(Vector v)
    {
        return new Point(x + v.x, y + v.y);
    }

    public double distance(Point p)
    {
        double dx = x - p.x;
        double dy = y - p.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
