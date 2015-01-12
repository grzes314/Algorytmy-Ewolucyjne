
package optimization;

import java.util.Random;

/**
 *
 * @author Grzegorz Los
 */
public class RandomnessSource
{
    public static Random rand = new Random(System.currentTimeMillis());
    
    public static void reset(long seed)
    {
        rand = new Random(seed);
    }

}
