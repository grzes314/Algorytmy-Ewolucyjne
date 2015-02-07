
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
}
