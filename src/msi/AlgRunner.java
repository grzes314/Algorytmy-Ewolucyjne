
package msi;

import optimization.Function;
import optimization.Permutation;
import optimization.ValuedIndividual;

/**
 *
 * @author Grzegorz Los
 */
public interface AlgRunner
{
    ValuedIndividual<Permutation> run(Function<Permutation> F);
    
    void prepare(ProblemData pData);
    
    String getName();
    
    String getDesc();

}
