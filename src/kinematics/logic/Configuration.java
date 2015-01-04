
package kinematics.logic;

/**
 *
 * @author Grzegorz Los
 */
public class Configuration
{
    public double[] angle;

    public Configuration(int n)
    {
        angle = new double[n];
    }

    public Configuration(double initAngle[])
    {
        angle = new double[initAngle.length];
        for (int i = 0; i < initAngle.length; ++i)
            angle[i] = initAngle[i];
    }
}
