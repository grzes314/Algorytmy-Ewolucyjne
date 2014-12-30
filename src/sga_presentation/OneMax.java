
package sga_presentation;

import sga.BitString;
import sga.Function;
import sga.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public class OneMax implements Function<BitString>
{

    @Override
    public ValuedIndividual<BitString> value(BitString x)
    {
        int sum = 0;
        for (boolean bit: x.bits)
            if (bit)
                sum++;
        return new ValuedIndividual<>(x, sum);
    }
}
