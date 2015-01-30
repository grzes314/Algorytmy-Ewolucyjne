
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

    Rectangle(Rectangle rect)
    {
        from = rect.from;
        to = rect.to;
        pos = rect.pos;
        vel = rect.vel;
        h = rect.h;
        w = rect.w;
        backwards = rect.backwards;
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
        v.scaleTo(deltaTime * vel);
        boolean b = isReachingDestination(deltaTime);
        if (b)
        {
            pos = backwards ? from : to;
            backwards = !backwards;
        }
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
    
    public boolean intersects(Line line)
    {
        Line[] rLines = getLines();
        for (Line rLine: rLines)
            if (line.instersects(rLine))
                return true;
        return false;
    }
    
    public double getIntersectionLength(Line line)
    {
        Line[] lines = getLines();
        return getIntersectionLength(line, lines);
    }
    
    private double getIntersectionLength(Line line, Line[] myLines)
    {
        Point[] points = new Point[4];
        int i, j;
        for (i = 0; i < 4; ++i)
        {
            points[i] = line.getItersection(myLines[i]);
            if (points[i] != null)
                break;
        }
        for (j = i+1; j < 4; ++j)
        {
            points[j] = line.getItersection(myLines[j]);
            if (points[j] != null)
                break;
        }
        if (i < 4) //line gets into rectangle
        {
            if (j < 4) // line gets through rectangle
                return points[i].distance(points[j]);
            if (inside(line.p))
                return points[i].distance(line.p);
            else return points[i].distance(line.r);
            
        }
        else
            return 0.0;        
    }
    
    public double getIntersectionLength(Line[] lines)
    {
        Line[] myLines = getLines();
        double sum = 0.0;
        for (Line line: lines)
            sum += getIntersectionLength(line, myLines);
        return sum;
    }

    private boolean inside(Point p)
    {
        return p.x > pos.x - w/2 &&
            p.x < pos.x + w/2 &&
            p.y > pos.y - h/2 &&
            p.y < pos.y + h/2;
    }
}
