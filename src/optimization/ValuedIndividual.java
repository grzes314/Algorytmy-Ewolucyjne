
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
    public final double infeasibility;

    public ValuedIndividual(Individual ind)
    {
        this.ind = ind;
        value = Double.NaN;
        feasible = true;
        valued = false;
        infeasibility = 0.0;
    }

    public ValuedIndividual(Individual ind, boolean feasible, double infeasibility)
    {
        this.ind = ind;
        this.feasible = feasible;
        value = Double.NaN;
        valued = false;
        this.infeasibility = infeasibility;
    }

    public ValuedIndividual(Individual ind, double value)
    {
        this.ind = ind;
        this.value = value;
        feasible = true;
        valued = true;
        if (value == Double.NaN)
            throw new RuntimeException("Wrong value");
        infeasibility = 0.0;
    }

    public ValuedIndividual(Individual ind, double value, boolean feasible, double infeasibility)
    {
        this.ind = ind;
        this.value = value;
        this.feasible = feasible;
        valued = true;
        this.infeasibility = infeasibility;
    }

    public ValuedIndividual(Individual ind, double value, boolean feasible)
    {
        this.ind = ind;
        this.value = value;
        this.feasible = feasible;
        valued = true;
        if (feasible)
            infeasibility = 0.0;
        else
            infeasibility = Double.POSITIVE_INFINITY;
    }
}
