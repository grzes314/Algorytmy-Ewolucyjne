
package sga_presentation;

import sga.BitString;
import sga.Function;

/**
 *
 * @author Grzegorz Los
 */
public class OneMax implements Function<BitString>
{

    @Override
    public double value(BitString x)
    {
        int sum = 0;
        for (boolean bit: x.bits)
            if (bit)
                sum++;
        return sum;
    }

}
