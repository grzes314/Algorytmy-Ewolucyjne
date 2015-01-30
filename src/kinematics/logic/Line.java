
package kinematics.logic;

import static java.lang.Math.*;

/**
 *
 * @author Grzegorz Los
 */
public class Line
{
    public final double a, b, c;
    public final double midX, midY, xRange, yRange;
    public static final double eps = 0.0001;
    public final Point p, r;
    
    public Line(Point p, Point r)
    {
        this.p = p;
        this.r = r;
        double u = r.x - p.x;
        double v = r.y - p.y;
        a = v;
        b = -u;
        c = u * p.y - v * p.x;
        double minX = min(p.x, r.x);
        double maxX = max(p.x, r.x);
        double minY = min(p.y, r.y);
        double maxY = max(p.y, r.y);
        midX = (maxX + minX) / 2;
        midY = (maxY + minY) / 2;
        xRange = (maxX - minX) / 2;
        yRange = (maxY - minY) / 2;
    }
    
    public boolean onLine(Point p)
    {
        return onLine(p.x, p.y);
    }
    
    public boolean onLine(double x, double y)
    {
        double val = a * x + b * y + c;
        if (abs(val) > eps)
            return false;
        return abs(x - midX) < xRange + eps && abs(y - midY) < yRange + eps;
    }
    
    public boolean instersects(Line l)
    {
        double det = a*l.b - b * l.a;
        if (abs(det) < eps)
            return false;
        double det_x = b * l.c - c * l.b;
        double det_y = c * l.a - a * l.c;
        double x = det_x / det;
        double y = det_y / det;
        return onLine(x, y) && l.onLine(x, y);
    }

    public Point getItersection(Line l)
    {
        double det = a*l.b - b * l.a;
        if (abs(det) < eps)
            return null;
        double det_x = b * l.c - c * l.b;
        double det_y = c * l.a - a * l.c;
        double x = det_x / det;
        double y = det_y / det;
        if ( onLine(x, y) && l.onLine(x, y) )
            return new Point(x,y);
        else
            return null;
    }
}
