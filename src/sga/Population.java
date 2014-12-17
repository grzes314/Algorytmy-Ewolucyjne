
package sga;

import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 * @param <Individual>
 */
public interface Population<Individual>
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
}
