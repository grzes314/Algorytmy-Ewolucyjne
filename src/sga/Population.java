
package sga;

import optimization.Copyable;
import optimization.ValuedIndividual;
import optimization.Function;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface Population<Individual extends Copyable<Individual>>
{
    public void evaluate(Function<Individual> F);
    
    public boolean terminationCondition();

    public double getMaxValue();

    public double getMinValue();

    public double getMeanValue();
    
    public Individual getMaxIndividual();
    
    public Individual getMinIndividual();
    
    public int getSize();
    
    public Individual getIndividual(int i);
    
    public ArrayList<ValuedIndividual<Individual>> createListOfSortedIndividuals();
    
    public List<Individual> getSolutions();
}
