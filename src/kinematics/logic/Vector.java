package kinematics.logic;

import static java.lang.Math.*;

/**
 *
 * @author Grzegorz Los
 */
public class Vector
{
    public double x, y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector(Vector v)
    {
        x = v.x;
        y = v.y;
    }
    
    public Vector(Point from, Point to)
    {
        x = to.x - from.x;
        y = to.y - from.y;
    }
    
    public double normSq()
    {
        return x*x + y*y;
    }
    
    public double norm()
    {
        return sqrt(normSq());
    }
    
    public double dot(Vector v)
    {
        return x * v.x + y * v.y;
    }
    
    public Vector rotate(double angle)
    {
        double s = sin(angle);
        double c = cos(angle);
        return new Vector(
            x*c - y*s,
            x*s + y*c
        );
    }
    
    void scaleTo(double newLength)
    {
        double p = newLength / norm();
        scaleBy(p);
    }
    
    void scaleBy(double p)
    {
        x *= p;
        y *= p;
    }
}
