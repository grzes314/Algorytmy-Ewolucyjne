
package pbil;

import java.util.Random;
import static pbil.ZeroOne.ONE;
import static pbil.ZeroOne.ZERO;

/**
 *
 * @author grzes
 */
public class BinaryDistr implements ImprovableDistr<ZeroOne>
{
    public BinaryDistr()
    {
        p = 0.5;
    }

    BinaryDistr(double p)
    {
        this.p = p;
    }
    
    @Override
    public void improve(ZeroOne obs, double learnRate)
    {
        int x = obs.val();
        p = p * (1 - learnRate) + x * learnRate;
    }

    @Override
    public ZeroOne draw()
    {
        return rand.nextDouble() < p ? ONE : ZERO;
    }
    
    @Override
    public void mutate(double mutationProb, double mutationRate)
    {
        if (rand.nextDouble() < mutationProb)
            p = p * (1 - mutationRate) + fiftyFifty.draw().val() * mutationRate;
    }
    
    private double p;
    private static final BinaryDistr fiftyFifty = new BinaryDistr(0.5);
    private static Random rand = new Random(System.currentTimeMillis());
}
