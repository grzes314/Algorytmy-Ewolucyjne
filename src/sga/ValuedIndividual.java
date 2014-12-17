
package sga;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class ValuedIndividual<Individual>
{
    public final Individual ind;
    public final double value;

    public ValuedIndividual(Individual ind, double value)
    {
        this.ind = ind;
        this.value = value;
    }
}
