
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Utils
{
    public static double radToDeg(double angle)
    {
        return angle * 180.0 / Math.PI;
    }
    
    public static double degToRad(double angle)
    {
        return angle * Math.PI / 180.0 ;
    }
    
    public static LocInCell getLocInCell(Dir d1, Dir d2)
    {
        if (oneIs(Dir.Up, d1, d2) && oneIs(Dir.Left, d1, d2))
            return LocInCell.UpperLeft;
        if (oneIs(Dir.Up, d1, d2) && oneIs(Dir.Right, d1, d2))
            return LocInCell.UpperRight;
        if (oneIs(Dir.Down, d1, d2) && oneIs(Dir.Left, d1, d2))
            return LocInCell.LowerLeft;
        if (oneIs(Dir.Down, d1, d2) && oneIs(Dir.Right, d1, d2))
            return LocInCell.LowerRight;
        else
            throw new RuntimeException("Invalid directions");
    }

    private static boolean oneIs(Dir what, Dir cand1, Dir cand2)
    {
        return cand1 == what || cand2 == what;
    }
    
    public static Dir rightOf(Dir dir)
    {
        switch(dir)
        {
            case Up:
                return Dir.Right;
            case Right:
                return Dir.Down;
            case Down:
                return Dir.Left;
            case Left:
                return Dir.Up;
            default:
                throw new RuntimeException("Impossible");
        }
    }
    
    public static Dir leftOf(Dir dir)
    {
        switch(dir)
        {
            case Up:
                return Dir.Left;
            case Right:
                return Dir.Up;
            case Down:
                return Dir.Right;
            case Left:
                return Dir.Down;
            default:
                throw new RuntimeException("Impossible");
        }
    }
    
    public static Dir opposite(Dir dir)
    {
        switch(dir)
        {
            case Up:
                return Dir.Down;
            case Right:
                return Dir.Left;
            case Down:
                return Dir.Up;
            case Left:
                return Dir.Right;
            default:
                throw new RuntimeException("Impossible");
        }
    }
    
    public enum LocInCell {
        UpperLeft, UpperRight, LowerRight, LowerLeft, Middle
    }
    
    public enum Dir {
        Up, Right, Down, Left
    }
}
