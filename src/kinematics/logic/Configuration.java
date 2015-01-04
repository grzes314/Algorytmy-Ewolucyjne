
package kinematics.logic;

import sga.Copyable;

/**
 *
 * @author Grzegorz Los
 */
public class Configuration implements Copyable<Configuration>
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

    @Override
    public Configuration getCopy()
    {
        Configuration conf = new Configuration(angle.length);
        for (int i = 0; i < angle.length; ++i)
            conf.angle[i] = angle[i];
        return conf;
    }
}
