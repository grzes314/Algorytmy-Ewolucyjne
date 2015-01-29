
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
    public final boolean valued;

    public ValuedIndividual(Individual ind)
    {
        this.ind = ind;
        value = Double.NaN;
        feasible = true;
        valued = false;
    }

    public ValuedIndividual(Individual ind, boolean feasible)
    {
        this.ind = ind;
        this.feasible = feasible;
        value = Double.NaN;
        valued = false;
    }

    public ValuedIndividual(Individual ind, double value)
    {
        this.ind = ind;
        this.value = value;
        feasible = true;
        valued = true;
        if (value == Double.NaN)
            throw new RuntimeException("Wrong value");
    }

    public ValuedIndividual(Individual ind, double value, boolean feasible)
    {
        this.ind = ind;
        this.value = value;
        this.feasible = feasible;
        valued = true;
        if (value == Double.NaN)
            throw new RuntimeException("Wrong value");
    }
}
