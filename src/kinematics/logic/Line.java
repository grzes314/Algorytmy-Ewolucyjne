
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
    
    public Line(Point p, Point r)
    {
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
        double nominator = c * l.a - a * l.c;
        double y = nominator / det;
        double x = 0;
        if (abs(a) < eps)
            x = (-l.b * y - l.c) / l.a;
        else
            x = (-b * y - c) / a;
        return onLine(x, y);
    }
}
