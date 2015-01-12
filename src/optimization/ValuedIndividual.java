
package optimization;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public class ValuedIndividual<Individual>
{
    public final Individual ind;
    public final double value;
    public final boolean feasible;

    public ValuedIndividual(Individual ind, double value)
    {
        this.ind = ind;
        this.value = value;
        feasible = true;
    }

    public ValuedIndividual(Individual ind, double value, boolean feasible)
    {
        this.ind = ind;
        this.value = value;
        this.feasible = feasible;
    }
}
