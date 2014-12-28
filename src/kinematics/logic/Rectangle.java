
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Rectangle
{
    public final Point from;
    public final Point to;
    private Point pos;
    public final double vel;
    public final double h, w;
    private boolean backwards = false;

    public Rectangle(Point from, Point to, double v, double h, double w)
    {
        this.from = from;
        this.to = to;
        this.vel = v;
        this.h = h;
        this.w = w;
        pos = from;
    }
    
    public Line[] getLines()
    {
        Line[] lines = new Line[4];
        Point p1 = new Point(pos.x - w/2, pos.y - h/2);
        Point p2 = new Point(pos.x + w/2, pos.y - h/2);
        Point p3 = new Point(pos.x + w/2, pos.y + h/2);
        Point p4 = new Point(pos.x - w/2, pos.y + h/2);
        lines[0] = new Line(p1, p2);
        lines[1] = new Line(p2, p3);
        lines[2] = new Line(p3, p4);
        lines[3] = new Line(p4, p1);
        return lines;
    }
    
    public void move(double deltaTime)
    {
        Vector v = backwards ? new Vector(to, from) : new Vector(from, to);
        v.scaleBy(deltaTime * vel);
        boolean b = isReachingDestination(deltaTime);
        if (b)
            backwards = !backwards;
        else
            pos = pos.plus(v);
    }

    private boolean isReachingDestination(double deltaTime)
    {
        Point dest = backwards ? from : to;
        double d = pos.distance(dest);
        return d < vel * deltaTime;
    }

    public Point getPos()
    {
        return pos;
    }
}
